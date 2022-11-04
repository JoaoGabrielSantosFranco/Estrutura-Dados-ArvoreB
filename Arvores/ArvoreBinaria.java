import java.util.function.Consumer;

public class ArvoreBinaria<T extends Comparable<T>> {
    class Elemento {
        Elemento pai;
        Elemento esquerda;
        Elemento direita;
        T valor;
    
        public Elemento(T valor) {
            this.valor = valor;
        }
    }
  
    private Elemento raiz;

    public boolean isVazia() {
        return raiz == null;
    }

    public Elemento adicionar(T valor) {
        Elemento e = new Elemento(valor);
        Elemento pai = this.raiz;

        while (pai != null) {
            if (valor.compareTo(pai.valor) < 0) {
                if (pai.esquerda == null) {
                    e.pai = pai;
                    pai.esquerda = e;
                    return e;
                } else {
                    pai = pai.esquerda;
                }
            } else {
                if (pai.direita == null) {
                    e.pai = pai;
                    pai.direita = e;
                    return e;
                } else {
                    pai = pai.direita;
                }
            }
        }

        this.raiz = e;
        return e;
    }
    
    public Elemento adicionar(Elemento pai, T valor) {
        Elemento e = new Elemento(valor);
        
        e.pai = pai;
         
        if (pai == null) {
            raiz = e;
        }
        
        return e;
    }

    public void remover(Elemento e) {
        if (e.esquerda != null)
            remover(e.esquerda);  
        
        if (e.direita != null)
            remover(e.direita);
        
        if (e.pai == null) {
            raiz = null;
        } else {
            if (e.pai.esquerda == e) {
                e.pai.esquerda = null;
            } else {
                e.pai.direita = null;
            }
        }
    }

    public void percorrer(Elemento e, Consumer<T> callback) {
        if (e != null) {
            if (e.valor == (Integer) 5) {
                System.out.println("");
            }
            
            callback.accept(e.valor);
            percorrer(e.esquerda, callback);
            percorrer(e.direita, callback);
        }
    }

    public Elemento pesquisar(Elemento e, T valor) {
        while (e != null) {
            if (e.valor.equals(valor)) {
                return e;
            } else if (valor.compareTo(e.valor) > 0) {
                e = e.direita;
            } else {
                e = e.esquerda;
            }
        }
        
        return null;
    }

    public int caminho(Elemento e) {
        int contador = 1;

        while (e.pai != null) { //Enquanto não alcançamos a raiz
            contador++;
            e = e.pai;
        }

        return contador;
    }

    public void percorrerInOrder(Elemento e, Consumer<T> callback) {
        if (e != null) {
            percorrerInOrder(e.esquerda, callback);
            callback.accept(e.valor);
            percorrerInOrder(e.direita, callback);
        }
    }

    public void percorrerPosOrder(Elemento e, Consumer<T> callback) {
        if (e != null) {
            percorrerPosOrder(e.esquerda, callback);
            percorrerPosOrder(e.direita, callback);
            callback.accept(e.valor);
        }
    }

    public void percorrer(Consumer<T> callback) {
        this.percorrer(raiz, callback);
    }

    public void percorrerInOrder(Consumer<T> callback) {
        this.percorrerInOrder(raiz, callback);
    }

    public void percorrerPosOrder(Consumer<T> callback) {
        this.percorrerPosOrder(raiz, callback);
    }

    public void percorrerLargura(Consumer<T> callback) {
        Fila<ArvoreBinaria<T>.Elemento> fila = new Fila<>();

        fila.adicionar(raiz);

        while (!fila.isVazia()) {
            ArvoreBinaria<T>.Elemento e = fila.remover();

            //visitando o valor do elemento atual
            callback.accept(e.valor); 

            if (e.esquerda != null) {
                fila.adicionar(e.esquerda);
            }

            if (e.direita != null) {
                fila.adicionar(e.direita);
            }
        }
    }

    public void percorrerProfundidade(Consumer<T> callback) {
        Pilha<ArvoreBinaria<T>.Elemento> pilha = new Pilha<>();

        pilha.adicionar(raiz);

        while (!pilha.isVazia()) {
            ArvoreBinaria<T>.Elemento e = pilha.remover();

            if (e.valor == (Integer) 5) {
                System.out.println("");
            }

            //visitando o valor do elemento atual
            callback.accept(e.valor); 

            if (e.direita != null) {
                pilha.adicionar(e.direita);
            }

            if (e.esquerda != null) {
                pilha.adicionar(e.esquerda);
            }
        }
    }

    public static void main(String args[]) {
        ArvoreBinaria<Integer> a = new ArvoreBinaria<>();
        
        // for (int i = 1; i <= 9; i++) {
        //     a.adicionar(i);
        // }

        a.adicionar(4);
        a.adicionar(2);
        a.adicionar(8);
        a.adicionar(1);
        a.adicionar(3);
        a.adicionar(6);
        a.adicionar(9);
        a.adicionar(5);
        a.adicionar(7);

        /*
        ArvoreBinaria<Integer>.Elemento n4 = a.adicionar(null, 4);
        ArvoreBinaria<Integer>.Elemento n2 = a.adicionar(n4, 2);
        ArvoreBinaria<Integer>.Elemento n8 = a.adicionar(n4, 8);
        ArvoreBinaria<Integer>.Elemento n1 = a.adicionar(n2, 1);
        ArvoreBinaria<Integer>.Elemento n3 = a.adicionar(n2, 3);
        ArvoreBinaria<Integer>.Elemento n6 = a.adicionar(n8, 6);
        ArvoreBinaria<Integer>.Elemento n9 = a.adicionar(n8, 9);
        ArvoreBinaria<Integer>.Elemento n5 = a.adicionar(n6, 5);
        ArvoreBinaria<Integer>.Elemento n7 = a.adicionar(n6, 7);

        n4.esquerda = n2;
        n4.direita = n8;

        n2.esquerda = n1;
        n2.direita = n3;

        n8.esquerda = n6;
        n8.direita = n9;

        n6.esquerda = n5;
        n6.direita = n7;
        */

        ArvoreBinaria<Integer>.Elemento e = a.pesquisar(a.raiz, 7);

        a.percorrer(v -> System.out.println(v));

        if (e != null) {
            System.out.println("Encontrou: " + a.caminho(e));
        } else {
            System.out.println("Não encontrou");
        }
    }
}