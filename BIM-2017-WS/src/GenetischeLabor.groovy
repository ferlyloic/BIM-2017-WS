
unreifeDNA_key = "Transkription DNA NR1H4 chromosome 12, 100867679:100957641"
//String dateipfad =/C:\git\BIM-2017-WS/

File file = new File( /..\files\NR1H4-BIM-Uebung.fasta/)
fastaData = FastaConnector.readData(file)
String unreifeDNAString = fastaData[unreifeDNA_key];
String unreifeRNAString = DNA_Object.toRnaString(unreifeDNAString)
fastaData[unreifeDNA_key+" (RNA)"] = unreifeRNAString
String reifeRNAString = ''
for(int i = 1; i < 12;i++) {
    fastaData["Exon $i (RNA)"] = DNA_Object.toRnaString(fastaData["Exon $i"])
    reifeRNAString +=fastaData["Exon $i (RNA)"]
}
//println reifeRNAString
fastaData +=["NR1H4-reife-mRNA":reifeRNAString]
FastaConnector.writeData(fastaData,"NR1H4-reife-mRNA", "NR1H4-reife-mRNA")
int starcodon = DNA_Object.getStartcodonIndex(reifeRNAString)
println "reife RNA-length is ${reifeRNAString.length()}"
println "reife RNA at startcodon ${reifeRNAString.substring(starcodon)}"
println "first Startcodon-Index is = $starcodon"
println "Stopcodon-Index is = ${DNA_Object.getStopcodonIndex(starcodon, reifeRNAString)}"
String rnaSequenceToTranslate = DNA_Object.getSequenceToTranslate(reifeRNAString)
println"Translatable sequence is $rnaSequenceToTranslate"
int firstExonIndex = DNA_Object.getIndex(unreifeRNAString, fastaData["Exon 1 (RNA)"])
int secondExonIndex = DNA_Object.getIndex(unreifeRNAString, fastaData["Exon 2 (RNA)"])
println "Exon-1-Startindex ist = $firstExonIndex"
println "Exon-2-Startindex ist = $secondExonIndex"
//String intron_1 = unreifeRNAString.substring(fastaData["Exon 1 (RNA)"].toString().length(), DNA_Object.getIndex(unreifeRNAString,fastaData["Exon 2 (RNA)"]))
String firstIntron = DNA_Object.getFirstIntron(unreifeRNAString, fastaData["Exon 1 (RNA)"], fastaData["Exon 2 (RNA)"])
String lastIntron = DNA_Object.getLastIntron(unreifeRNAString, fastaData["Exon 10 (RNA)"], fastaData["Exon 11 (RNA)"])
println "erste Intron hat die Laenge ${firstIntron.length()} : \n$firstIntron"
println "letzte Intron hat die Laenge ${lastIntron.length()} : \n$lastIntron"
String firstIntronHeader = 'First Intron (RNA)'
String lastIntronHeader = 'Last Intron (RNA)'
String rnaSequenceToTranslateHeader = "RNA Sequence to translate"
fastaData+=[(firstIntronHeader): firstIntron]
fastaData+=[(lastIntronHeader): lastIntron]
fastaData+=[(rnaSequenceToTranslateHeader): rnaSequenceToTranslate]
FastaConnector.writeData(fastaData, "NR1H4-Introns", firstIntronHeader, lastIntronHeader)
Set rnaSequences = fastaData.findAll {it.key.toString().contains('RNA')}.keySet()
FastaConnector.writeData(fastaData,"NR1H4-BIM-Uebung (RNA)",rnaSequences.sort().toArray())

