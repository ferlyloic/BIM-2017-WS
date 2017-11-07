class Nucleotid_Object {
    static String toRnaString(String dna) {
        dna.replace('T','U')
    }
    static int getIndex(String dna, String firstExon){
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
    static int getStartcodonIndex(String sequence){
        String startcodon = "AUG"
        int index = getIndex(sequence,startcodon)
        println "erste Startcodon ist ${index+1}"
        return index
    }
    static int getStopcodonIndex(int startcodonIndex, String sequence){
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
                    println "erste Stopcodon ist ${i+1}"
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
    static String getFirstIntron(String RNA, String exon_1, String exon_2){
        int index = getIndex(RNA, exon_1)
        if(index > 0){
            return RNA.substring(0, index)
        }else{
        return RNA.substring(exon_1.length(), getIndex(RNA,exon_2))
        }
    }
    static String getLastIntron(String RNA, String lastExon, String beforeLastExon){
        return getFirstIntron(RNA.reverse(),lastExon.reverse(), beforeLastExon.reverse()).reverse()
    }
    static String getSequenceToTranslate(String rnaSequence){
        int startcodon = getStartcodonIndex(rnaSequence)
        int stopcodonIndex = getStopcodonIndex(startcodon,rnaSequence)
        rnaSequence.substring(startcodon,stopcodonIndex+3)
    }
    static String rnaToProtein(String rna){
        def map = ["UUU":"F", "UUC":"F", "UUA":"L", "UUG":"L",
            "UCU":"S", "UCC":"S", "UCA":"S", "UCG":"S",
            "UAU":"Y", "UAC":"Y", "UAA":"*", "UAG":"*",
            "UGU":"C", "UGC":"C", "UGA":"*", "UGG":"W",
            "CUU":"L", "CUC":"L", "CUA":"L", "CUG":"L",
            "CCU":"P", "CCC":"P", "CCA":"P", "CCG":"P",
            "CAU":"H", "CAC":"H", "CAA":"Q", "CAG":"Q",
            "CGU":"R", "CGC":"R", "CGA":"R", "CGG":"R",
            "AUU":"I", "AUC":"I", "AUA":"I", "AUG":"M",
            "ACU":"T", "ACC":"T", "ACA":"T", "ACG":"T",
            "AAU":"N", "AAC":"N", "AAA":"K", "AAG":"K",
            "AGU":"S", "AGC":"S", "AGA":"R", "AGG":"R",
            "GUU":"V", "GUC":"V", "GUA":"V", "GUG":"V",
            "GCU":"A", "GCC":"A", "GCA":"A", "GCG":"A",
            "GAU":"D", "GAC":"D", "GAA":"E", "GAG":"E",
            "GGU":"G", "GGC":"G", "GGA":"G", "GGG":"G"]
        String result = ''
        String codon = ''
        int j = rna.length() % 3
        if (j == 0){
            for(int i = 0; i < rna.length(); i+=3){
                codon = rna.substring(i, i+3)
               // println(codon)
                // println(map[codon])
                result +=map[codon]
            }
        }

        return result
    }
}
