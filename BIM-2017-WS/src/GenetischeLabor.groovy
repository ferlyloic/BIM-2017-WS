
unreifeDNA_key = "Transkription DNA NR1H4 chromosome 12, 100867679:100957641"
String dateipfad =/C:\git\BIM-2017-WS/

File file = new File(dateipfad + /\files\NR1H4-BIM-Uebung.fasta/)
fastaData = FastaConnector.readData(file);
int getIndex(String dna, String firstExon){
    for(int i = 0; i < dna.length()-firstExon.length();i++){
        int counter = 0
        for(int j = 0; j < firstExon.length(); j++){
            if(dna[i+j]!=firstExon[j]){
                break
            }
            ++counter
            if(counter == firstExon.length()){
                return i;
            }
        }

    }
    return -1
}
int getStartcodonIndex(String sequence){
    String startcodon = "AUG"
    return getIndex(sequence,startcodon)
}
int getStopcodonIndex(int startcodonIndex, String sequence){
    String stopcodon1 = "UAG"
    String stopcodon2 = "UGA"
    String stopcodon3 = "UAA"
    String codon
    print "*"+sequence.substring( startcodonIndex, startcodonIndex + 3) + "*"
    for(int i = startcodonIndex+3; i < sequence.length(); i+=3){

        if(i+3 <= sequence.length()) {
            codon = sequence.substring(i,i+3)
            if (codon == stopcodon1 || codon == stopcodon2 || codon == stopcodon3) {
                println "[$codon]|"
                return i
            } else {
                print codon + "|"
            }
        }else {
            print sequence.substring(i)
        }
    }
    println()
    return -1
}

toRnaString = {String dna -> dna.replace('T','U')}

String unreifeDNAString = fastaData[unreifeDNA_key];
String unreifeRNAString = toRnaString(unreifeDNAString)
fastaData[unreifeDNA_key+" (RNA)"] = unreifeRNAString
String reifeRNAString = ''
for(int i = 1; i < 12;i++) {
    fastaData["Exon $i (RNA)"] = toRnaString(fastaData["Exon $i"])
    reifeRNAString +=fastaData["Exon $i (RNA)"]
}

Set rnaSequences = fastaData.findAll {it.key.toString().contains(' (RNA)')}.keySet()
FastaConnector.writeData(fastaData,"NR1H4-BIM-Uebung (RNA)",rnaSequences.sort().toArray())
//println reifeRNAString
fastaData +=["NR1H4-reife-mRNA":reifeRNAString]
FastaConnector.writeData(fastaData,"NR1H4-reife-mRNA", "NR1H4-reife-mRNA")
int starcodon = getStartcodonIndex(reifeRNAString)
println "reife RNA-length is ${reifeRNAString.length()}"
println "reife RNA at startcodon ${reifeRNAString.substring(starcodon)}"
println "first Startcodon-Index is = ${starcodon}"

println "Stopcodon-Index is = ${getStopcodonIndex(starcodon, reifeRNAString)}"

println "Exon-1-Startindex ist = ${getIndex(unreifeRNAString, fastaData["Exon 1 (RNA)"])}"
println "Exon-2-Startindex ist = ${getIndex(unreifeRNAString, fastaData["Exon 2 (RNA)"])}"
String intron_1 = unreifeRNAString.substring(fastaData["Exon 1 (RNA)"].toString().length(), getIndex(unreifeRNAString,fastaData["Exon 2 (RNA)"]))
println "Intron 1 hat die Laenge ${intron_1.length()} : \n$intron_1"
String intron_1_Header = 'Intron 1(RNA)'
fastaData+=[(intron_1_Header): intron_1]
FastaConnector.writeData(fastaData, "NR1H4-Intron-1", intron_1_Header)

