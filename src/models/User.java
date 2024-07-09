package models;

public class User {
    private String username;
    private String password;
    private TipoUser tipoUser;

    public User(String username, String password, TipoUser tipoUser) {
        this.username = username;
        this.password = password;
        this.tipoUser = tipoUser;
    }


    
    /** 
     * @return String
     */
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public TipoUser getTipoUsuario() {
        return tipoUser;
    }
    public enum TipoUser {
        ADMINISTRADOR, CONSULTOR, EXTERNO
    }
    @Override
        public String toString() {
            return "Usuario{" +
                    "username='" + username + '\'' +
                    ", senha='" + password + '\'' +
                    ", tipoUsuario=" + tipoUser +
                    '}';
        }
}