
unreifeDNA_key = "Transkription DNA NR1H4 chromosome 12, 100867679:100957641"
//String dateipfad =/C:\git\BIM-2017-WS/

File file = new File( /..\files\NR1H4-BIM-Uebung.fasta/)
fastaData = FastaConnector.readData(file)
String unreifeDNAString = fastaData[unreifeDNA_key];
String unreifeRNAString = Nucleotid_Object.toRnaString(unreifeDNAString)
fastaData[unreifeDNA_key+" (RNA)"] = unreifeRNAString
String reifeRNAString = ''
for(int i in 1 .. 11) {
    fastaData["Exon $i (RNA)"] = Nucleotid_Object.toRnaString(fastaData["Exon $i"])
    reifeRNAString +=fastaData["Exon $i (RNA)"]
}
//println reifeRNAString
fastaData +=["NR1H4-reife-mRNA":reifeRNAString]
FastaConnector.writeData(fastaData,"NR1H4-reife-mRNA", "NR1H4-reife-mRNA")
int starcodon = Nucleotid_Object.getStartcodonIndex(reifeRNAString)
println "reife RNA-length is ${reifeRNAString.length()}"
println "reife RNA at startcodon ${reifeRNAString.substring(starcodon)}"
println "first Startcodon-Index is = $starcodon"
println "Stopcodon-Index is = ${Nucleotid_Object.getStopcodonIndex(starcodon, reifeRNAString)}"
String rnaSequenceToTranslate = Nucleotid_Object.getSequenceToTranslate(reifeRNAString)
println"Translatable sequence is $rnaSequenceToTranslate"
int firstExonIndex = Nucleotid_Object.getIndex(unreifeRNAString, fastaData["Exon 1 (RNA)"])
int secondExonIndex = Nucleotid_Object.getIndex(unreifeRNAString, fastaData["Exon 2 (RNA)"])
println "Exon-1-Startindex ist = $firstExonIndex"
println "Exon-2-Startindex ist = $secondExonIndex"
//String intron_1 = unreifeRNAString.substring(fastaData["Exon 1 (RNA)"].toString().length(), Nucleotid_Object.getIndex(unreifeRNAString,fastaData["Exon 2 (RNA)"]))
String firstIntron = Nucleotid_Object.getFirstIntron(unreifeRNAString, fastaData["Exon 1 (RNA)"], fastaData["Exon 2 (RNA)"])
String lastIntron = Nucleotid_Object.getLastIntron(unreifeRNAString, fastaData["Exon 10 (RNA)"], fastaData["Exon 11 (RNA)"])
println "erste Intron hat die Laenge ${firstIntron.length()} : \n$firstIntron"
println "letzte Intron hat die Laenge ${lastIntron.length()} : \n$lastIntron"
String firstIntronHeader = 'First Intron (RNA)'
String lastIntronHeader = 'Last Intron (RNA)'
String rnaSequenceToTranslateHeader = "RNA Sequence to translate"
fastaData+=[(firstIntronHeader): firstIntron]
fastaData+=[(lastIntronHeader): lastIntron]
fastaData+=[(rnaSequenceToTranslateHeader): rnaSequenceToTranslate]
String rnaVarianteTotranslate = ''
for(i in 1 .. 11){
    rnaVarianteTotranslate += fastaData["Exon $i (RNA)"]
    if(i == 1){
        rnaVarianteTotranslate += fastaData['First Intron (RNA)']
    }
}
fastaData+= ["reife RNA Sequence-variante":rnaVarianteTotranslate]
println rnaVarianteTotranslate
rnaVarianteTotranslate = Nucleotid_Object.getSequenceToTranslate(rnaVarianteTotranslate)
String rnaSequenceVarianteToTranslateHeader = "RNA Sequence-variante to translate"
fastaData+=[(rnaSequenceVarianteToTranslateHeader): rnaVarianteTotranslate]
String protein1 = Nucleotid_Object.rnaToProtein(rnaSequenceToTranslate)
println"Protein ist $protein1"
fastaData+=["NR1H4-Protein": protein1]
FastaConnector.writeData(fastaData,"NR1H4-Protein","NR1H4-Protein")
String protein2 = Nucleotid_Object.rnaToProtein(rnaVarianteTotranslate)
println"Proteinvariante ist $protein2"
fastaData+=["NR1H4-Protein-Variante": protein2]
FastaConnector.writeData(fastaData,"NR1H4-Protein-Variante","NR1H4-Protein-Variante")
FastaConnector.writeData(fastaData, "NR1H4-Introns", firstIntronHeader, lastIntronHeader)
Set rnaSequences = fastaData.findAll {it.key.toString().contains('RNA')}.keySet()
FastaConnector.writeData(fastaData,"NR1H4-BIM-Uebung (RNA)",rnaSequences.sort().toArray())

