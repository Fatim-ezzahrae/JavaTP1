package Model;

import org.mindrot.jbcrypt.BCrypt;

public class login {
    private int idEmp;
    private String username;
    private String hashedPassword;

    public login(int idEmp, String username, String password) {
        this.idEmp = idEmp;
        this.username = username;
        this.hashedPassword = hashPassword(password);
    }
    
    public login(String username, String password) {
        this.username = username;
        this.hashedPassword = hashPassword(password);
    }

    public int getIdEmp() {
        return idEmp;
    }

    public String getUsername() {
        return username;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    // Hash a plain text password
    private String hashPassword(String plainPassword) {
        return BCrypt.hashpw(plainPassword, BCrypt.gensalt());
    }

    // Verify if the plain password matches the hashed password
    public boolean verifyPassword(String plainPassword) {
        return BCrypt.checkpw(plainPassword, hashedPassword);
    }
}
