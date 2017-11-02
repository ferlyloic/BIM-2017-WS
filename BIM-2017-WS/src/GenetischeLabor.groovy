
unreifeDNA_key = "Transkription DNA NR1H4 chromosome 12, 100867679:100957641"
String dateipfad =/C:\git\BIM-2017-WS/
File file = new File(dateipfad + /\files\NR1H4-BIM-Uebung.fasta/)
println "${file.exists()}"
fastaData = FastaReader.extractData(file);
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
    for(int i = startcodonIndex; i < sequence.length(); i+=3){

        if(i+3 < sequence.length()) {
            codon = sequence.substring(i,i+3)
            if (codon == stopcodon1 || codon == stopcodon2 || codon == stopcodon3) {
                print "[$codon]"
                //return i
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
toRnaString= {String dna -> dna.replace('T','U')}
String unreifeDNAString = fastaData[unreifeDNA_key];
String unreifeRNAString = toRnaString(unreifeDNAString)
String reifeDNAString = ''
for(int i = 1; i < 11;i++) reifeDNAString += fastaData["Exon $i"]
String reifeRNAString = toRnaString(reifeDNAString)
println reifeRNAString
int starcodon = getStartcodonIndex(reifeRNAString)
println "reife RNA-length is ${reifeRNAString.length()}"
println "reife RNA at startcodon ${reifeRNAString.substring(starcodon)}"
println "first Startcodon-Index is = ${starcodon}"

println "Stopcodon-Index is = ${getStopcodonIndex(starcodon, reifeRNAString)}"
println unreifeDNAString.length()
println "index is = ${getIndex(unreifeRNAString,toRnaString(fastaData["Exon 1"]))}"
println "index is = ${getIndex(unreifeRNAString,toRnaString(fastaData["Exon 2"]))}"
String intron1 = unreifeRNAString.substring(fastaData["Exon 1"].toString().length(), getIndex(fastaData[unreifeDNA_key],fastaData["Exon 2"]))
println "Intron 1 hat die Laenge ${intron1.length()} : \n$intron1"
fastaData+=['Intron 1': intron1]
FileWriter writer = new FileWriter(dateipfad + "\\files\\NR1H4-Intron-1.fasta")
writer.write(">Intron 1\n")
String sequence = fastaData['Intron 1']
String sequenceFormated =""
int count = 0
for (char c: sequence){
    sequenceFormated += c
    if(++count==60){
        count=0
        sequenceFormated +='\n'.toCharacter()
    }
}
writer.write(sequenceFormated)
writer.close()
