public class ScytaleCipher{
    public static String encrypt(String plaintext, int key){
        int numberOfTurns=(plaintext.length()+key-1)/key;
        char [][] scytaleRod=new char[numberOfTurns][key];
        int index=0;
        for(int row=0;row<numberOfTurns;row++){
            for(int col=0;col<key;col++){
                if(index<plaintext.length()){
                    scytaleRod[row][col]=plaintext.charAt(index);
                }
                else{
                    scytaleRod[row][col]=' ';
                }
                index++;
            }
        }
        StringBuilder ciphertext=new StringBuilder();
        for(int col=0;col<key;col++){
            for(int row=0;row<numberOfTurns;row++){
                ciphertext.append(scytaleRod[row][col]);
            }
        }
        return ciphertext.toString();
    }
    public static String decrypt(String cipherText, int key) {
        return "Dekriptimi";
        public static String decrypt(String cipherText, int key) {
    int col = key;
    int row = (int) Math.ceil((double) cipherText.length() / key);
    char[][] cipherMatrix = new char[row][col];

    int index = 0;
    for (int r = 0; r < row; r++) {
        for (int c = 0; c < col; c++) {
            cipherMatrix[r][c] = cipherText.charAt(index);
            index++;
        }
    }

    StringBuilder plaintextBuilder = new StringBuilder();
    for (int c = 0; c < col; c++) {
        for (int r = 0; r < row; r++) {
            plaintextBuilder.append(cipherMatrix[r][c]);
        }
    }
    return plaintextBuilder.toString().trim();
}

    }
}
