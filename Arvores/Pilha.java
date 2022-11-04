public class Pilha<T> {
    class Elemento {
        Elemento anterior;
        T valor;

        public Elemento(T valor) {
            this.valor = valor;
        }
    }

    private Elemento topo;

    public boolean isVazia() {
        return topo == null;
    }

    public void adicionar(T valor) {
        Elemento e = new Elemento(valor);
        e.anterior = topo;
        
        topo = e;
    }

    public T remover() {
        if (!isVazia()) {
            T valor = topo.valor;
            topo = topo.anterior;

            return valor;
        }

        return null;
    }
}
