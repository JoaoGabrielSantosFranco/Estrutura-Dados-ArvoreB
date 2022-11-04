public class Fila<T> {
    class Elemento {
        Elemento proximo;
        T valor;

        public Elemento(T valor) {
            this.valor = valor;
        }
    }

    private Elemento cabeca, cauda;

    public boolean isVazia() {
        return cabeca == null && cauda == null;
    }

    public void adicionar(T valor) {
        Elemento e = new Elemento(valor);
        
        if (!isVazia()) {
            cauda.proximo = e;
        } else {
            cabeca = e;
        }

        cauda = e;
    }

    public T remover() {
        if (!isVazia()) {
            T valor = cabeca.valor;
            cabeca = cabeca.proximo;

            if (cabeca == null) {
                cauda = null;
            }

            return valor;
        }

        return null;
    }
}
