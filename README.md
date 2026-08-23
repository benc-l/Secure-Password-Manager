# Secure Password Manager (Java Client-Server)

A Java-based client-server application that acts as a secure password manager. This project demonstrates socket programming, network communication, and applied cryptography by implementing RSA encryption from scratch to securely transmit and store user credentials.

## Project Architecture
* **`Server.java`:** A multithreaded-capable server application that listens for client connections via Java Sockets on a designated port. It utilizes an in-memory `Hashtable` to efficiently store and retrieve website-password key-value pairs. 
* **`Client.java`:** A command-line interface (CLI) client that provides an interactive menu for users to store passwords, retrieve passwords, or terminate the session.

## Security Features
* **Custom RSA Implementation:** The client application implements the RSA algorithm from scratch utilizing Java's `BigInteger` class and custom modular exponentiation (`powmod`) to handle cryptographic calculations.
* **Client-Side Encryption (Zero-Knowledge Server):** Passwords are encrypted on the client side *before* being transmitted over the network. The server only ever receives, stores, and returns the ciphertext, ensuring that neither the server nor any network interceptor can read the plaintext passwords.

## Usage Instructions

To run this application, you will need to open two separate terminal windows (one for the server and one for the client). 

**1. Compile the code:**
```bash
javac Server.java
javac Client.java
