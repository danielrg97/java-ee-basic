package co.com.daniel.sga.datos;

import co.com.daniel.sga.domain.Usuario;
import java.util.List;

public interface UsuarioDao {
    public List<Usuario> getAllUsuarios();
    public Usuario getUsuarioById(Usuario usuario);
    public Usuario getUsuarioByUsername(Usuario usuario);
    public void saveUsuario(Usuario usuario);
    public void updateUsuario(Usuario usuario);
    public void deleteUsuario(Usuario usuario);
    
}
