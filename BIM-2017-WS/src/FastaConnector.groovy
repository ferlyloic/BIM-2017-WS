class FastaConnector {


    static HashMap readData(source){
        def data = new HashMap()
        try{
            InputStream flux = new FileInputStream(source);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            String key
            while ((ligne = buff.readLine() )!= null){
                // println ligne.charAt(0)
                if(ligne.charAt(0) == '>'.toCharacter()){
                    key = ligne.substring(1)
                    ligne = ""
                    data[key]= ''
                }
                data[key] +=ligne
                //System.out.println(ligne)
            }
            buff.close();
            println data.keySet()
            return data
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
    static void writeData(HashMap data,String dateiname,def ... myHeaders){
        FileWriter writer = new FileWriter("..\\files\\"+dateiname+".fasta")
        for(def key: myHeaders) {
        writer.write(">${key.toString()}\n")
            String sequence = data[key]
            String sequenceFormated = ""
            int count = 0
            for (char c : sequence) {
                sequenceFormated += c
                if (++count == 60) {
                    count = 0
                    sequenceFormated += '\n'.toCharacter()
                }
            }
            writer.write(sequenceFormated+"\n")
        }
        writer.close()
    }
}

