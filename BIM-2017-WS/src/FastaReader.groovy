class FastaReader {


    static HashMap extractData(source){
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
}

