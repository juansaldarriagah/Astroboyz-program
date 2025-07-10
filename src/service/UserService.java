package service 

import auth.User;
import auth.AdminUser;
import auth.NormalUser;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class UserService {
  private List<User> usuarios;

public UserService(){
  usuario = new ArrayList<>();
}
//register new users
public void registerUser(User user){
  usuarios.add(user);
  }
// search user by name
public User buscarPorUsername (String username){
  for (User user : usuarios){
    if (user.getUsername().equals(username))) {
      return user;
}
  }
  return null;
}
// credential verification for login
public User autenticar (String username , String password) {
  for (User user : usuarios){
    if (user.getUsername().equals(username) && user.getPassword().equals(password){
        return user ;
}
  }
    return null;
}

// get the full list of users
public List<User> getUsuarios(){
  return usuarios;
}
// save the users inside a file
public void guardarUsuarios (String rutaArchivo) throws IOException {
  try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(rutaArchivo))) {
    out.writeObject(usuarios);
  } 
}
// load the users from a file
public void cargarUsuarios (String rutaArchivo) throws IOException ClassNotFoundException {
  file archivo = new File(ruta archivo);
  if (!archivo.exsist()) {
    usuarios = new Arraylist<>();
    return;
  }
  try (ObjectInputStream in = new ObjectOutputStream (new FileOutputStream(rutaArchivo))){
    usuarios = (List<users>) in.readObject(); 
  }
}
}

  
