# Bifid Cipher Algorithm

## Introduction
The Bifid cipher is a classical encryption algorithm that combines the Polybius square with transposition and uses fractionation to achieve diffusion. It was invented by Felix Delastelle. This Java implementation provides a simple demonstration of the algorithm.

## Algorithm Description

### Encryption Process:
1. **Polybius Square Creation**: A 5x5 square is populated with the letters of the alphabet, mapping each letter to a pair of coordinates. If a secret key is used, it populates the square first, followed by the remaining letters of the alphabet.

2. **Fractionation**: Each letter in the plaintext is replaced by its coordinates.

3. **Transposition**: The row coordinates are listed first, followed by the column coordinates.

4. **Conversion**: The coordinates are then converted back to letters to form the ciphertext.

### Decryption Process:
Decryption involves reversing the encryption process, starting from the bifid coordinates to retrieve the original text.

# Scytale Cipher Algorithm

## Introduction
A scytale is a transposition cipher, used by ancient Greeks and Spartans during military campaigns. It consists of a cylindrical stick of a specific diameter around which a strip of paper is wrapped

## Algorithm Description

### Encryption 
The message is written on a strip of paper which is wrapped around the stick. When unwrapped from the stick, the message becomes difficult to read. To implement the encryption in code, we have first arranged the message in a matrix with a given number of columns from the user, which in our case is considered the key. The number of rows (turns) depends on the message length and the number of columns. The message then is read column-wise resulting in the ciphertext. 

### Decryption
To decipher the message, you would wrap the strip of paper around another stick with the same diameter as the one used for encryption. That’s why the diameter can be seen as a key. To implement the decryption in code, we read the text row-wise. To perform the decryption one must know the number of columns.
