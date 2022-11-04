import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Vector;
import java.util.function.Consumer;

public class ArvoreB<T extends Comparable<T>> {
    class Elemento {
        Elemento pai; // Referência para o elemento pai
        Vector<Elemento> filhos; // Vetor de elementos filho
        Vector<T> chaves; // Vetor de chaves (valores)

        public Elemento() {
            filhos = new Vector<>();
            chaves = new Vector<>();
        }
    }

    public static int count;
    private Elemento raiz;
    private int ordem;

    public ArvoreB(int ordem) {
        this.ordem = ordem;
        raiz = new Elemento();
    }

    public void percorre(Elemento e, Consumer<T> callback) {
        if (e != null) {
            int total = e.chaves.size();

            for (int i = 0; i < total; i++) {
                // Visita o filho da esquerda/direita
                percorre(e.filhos.get(i), callback);

                callback.accept(e.chaves.get(i));
            }

            // Visita ultimo filho (direita)
            percorre(e.filhos.get(total), callback);
        }
    }

    private boolean localizaChave(T chave) {
        Elemento e = raiz;

        while (e != null) {
            int i = pesquisaBinaria(e, chave);

            if (i < e.chaves.size() && e.chaves.get(i).compareTo(chave) == 0) {
                return true; // Encontrou
            } else {
                e = i < e.filhos.size() ? e.filhos.get(i) : null;
            }
        }

        return false; // Não encontrou
    }

    private int pesquisaBinaria(Elemento e, T chave) {
        int inicio = 0, fim = e.chaves.size() - 1, meio;

        while (inicio <= fim) {

            count++;

            meio = (inicio + fim) / 2;

            if (e.chaves.get(meio).compareTo(chave) == 0) {
                return meio; // Encontrou
            } else if (e.chaves.get(meio).compareTo(chave) > 0) {
                fim = meio - 1;
            } else {
                inicio = meio + 1;
            }
        }

        return inicio; // Não encontrou
    }

    private Elemento localizaNo(T chave) {
        Elemento e = raiz;

        while (e != null) {

            count++;

            int i = pesquisaBinaria(e, chave);
            Elemento filho = i < e.filhos.size() ? e.filhos.get(i) : null;

            if (filho != null)
                e = filho;
            else
                return e; // Encontrou nó
        }

        return null; // Não encontrou nenhum nó
    }

    private void adicionaChaveNo(Elemento e, Elemento novo, T chave) {
        count++;
        int aux = count;
        int i = pesquisaBinaria(e, chave);
        count = aux;
        e.chaves.insertElementAt(chave, i);

        if (e.filhos.size() == 0)
            e.filhos.insertElementAt(null, i);

        e.filhos.insertElementAt(novo, i + 1);
    }

    private boolean transbordo(Elemento e) {
        count++;
        return e.chaves.size() > ordem * 2;
    }

    private Elemento divideNo(Elemento e) {
        int meio = e.chaves.size() / 2;
        Elemento novo = new Elemento();
        novo.pai = e.pai;

        count++;

        for (int i = meio + 1; i <= e.chaves.size(); i++) {
            if (i < e.chaves.size()) {
                T v = e.chaves.get(i);
                novo.chaves.add(v);
            }
            Elemento filho = e.filhos.get(i);
            novo.filhos.add(filho);
            if (filho != null)
                filho.pai = novo;
        }

        e.chaves.subList(meio, e.chaves.size()).clear();
        e.filhos.subList(meio + 1, e.filhos.size()).clear();
        return novo;
    }

    private void adicionaChave(T chave) {
        Elemento e = localizaNo(chave);

        adicionaChaveRecursivo(e, null, chave);
    }

    void adicionaChaveRecursivo(Elemento e, Elemento novo, T chave) {
        adicionaChaveNo(e, novo, chave);

        // count++;

        if (transbordo(e)) {
            T promovido = e.chaves.get(ordem);
            novo = divideNo(e);

            if (e.pai == null) {
                Elemento pai = new Elemento();
                pai.filhos.add(e);
                adicionaChaveNo(pai, novo, promovido);
                e.pai = pai;
                novo.pai = pai;
                raiz = pai;
            } else
                adicionaChaveRecursivo(e.pai, novo, promovido);
        }

    }

    public static void main(String[] args) {
        ArvoreB<Integer> a = new ArvoreB<>(5);

        /*
         * for (int i = 1; i <= 1000; i++) {
         * a.adicionaChave(i);
         * System.out.println(count);
         * }
         */


        Integer[] array = new Integer[1000];
        for (int i = 0; i < 1000; i++) {
            array[i] = i;
        }
        
        List<Integer> list = Arrays.asList(array);
        Collections.shuffle(list);
        list.toArray(array);
        Integer[] integers = list.toArray(new Integer[1000]);

        for (int i = 0; i < 1000; i++) {
            a.adicionaChave(integers[i]);
            System.out.println(count);
        }


    }
}
