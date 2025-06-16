package model;

public class Curso {
    private String codigo;
    private String nome;
    private String area;

    public Curso(String codigo, String nome, String area) {
        this.codigo = codigo;
        this.nome = nome;
        this.area = area;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public String toString() {
        return codigo + ";" + nome + ";" + area;
    }
}
