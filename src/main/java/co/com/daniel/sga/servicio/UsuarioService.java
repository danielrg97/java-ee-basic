package co.com.daniel.sga.servicio;

import co.com.daniel.sga.domain.Usuario;
import java.util.List;
import javax.ejb.Local;

@Local
public interface UsuarioService {
    public List<Usuario> listarUsuarios();
    public Usuario encontrarUsuarioPorId(Usuario usuario);
    public Usuario encontrarUsuarioPorUsername(Usuario usuario);
    public void guardarUsuario(Usuario usuario);
    public void editarUsuario(Usuario usuario);
    public void eliminarUsuario(Usuario usuario);
}
