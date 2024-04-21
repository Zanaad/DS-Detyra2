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
