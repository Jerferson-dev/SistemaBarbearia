
public class Cliente {
    private String name;
    private String telefone;

    public Cliente(String name, String telefone) {
        this.name = name;
        this.telefone = telefone;
    }

    public Cliente(String name) {

        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }
}
