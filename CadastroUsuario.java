import java.util.ArrayList;


public class CadastroUsuario {
    ArrayList<Usuario> usuarios_registrados = new ArrayList<Usuario>();
    
    public void CadastrarUsuario(String nome, String email, int idade) {
        Usuario usuario = new Usuario(nome, email, idade);
        usuarios_registrados.add(usuario);
    }
}
