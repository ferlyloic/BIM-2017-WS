class FastaReader {
    def source
    def data = new HashMap()
    void extractData(){
        try{
            InputStream flux = new FileInputStream(source);
            InputStreamReader lecture = new InputStreamReader(flux);
            BufferedReader buff = new BufferedReader(lecture);
            String ligne;
            String key;
            String value = "";
            while ((ligne = buff.readLine() )!= null){
                // println ligne.charAt(0)
                if(ligne.charAt(0) == '>'.toCharacter()){
                    key = ligne.substring(1)
                    ligne = ""
                    value = ""
                    data.put(key, '')
                }
                data.put(key, (String)data.get(key)+ligne)
                //System.out.println(ligne)
            }
            buff.close();
            println data.keySet()
        }
        catch (Exception e){
            System.out.println(e.toString());
        }
    }
}

