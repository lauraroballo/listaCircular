package listacircular;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ListaCircularInterfaz extends JFrame {
    private ListaCircular listaCircular;

    private JTextArea txtArea;
    private JTextField txtDato;

    public ListaCircularInterfaz() {
        listaCircular = new ListaCircular();

        setTitle("Lista Circular Interfaz");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        txtArea = new JTextArea();
        panel.add(new JScrollPane(txtArea), BorderLayout.CENTER);

        JPanel btnPanel = new JPanel();
        JLabel lblDato = new JLabel("Dato:");
        txtDato = new JTextField(10);
        txtDato.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    insertarDato();
                }
            }
        });
        btnPanel.add(lblDato);
        btnPanel.add(txtDato);

        JButton btnInsertar = new JButton("Insertar");
        btnInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                insertarDato();
            }
        });
        btnPanel.add(btnInsertar);

        JButton btnOrdenar = new JButton("Ordenar");
        btnOrdenar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                listaCircular.ordenar();
                actualizarTextArea();
            }
        });
        btnPanel.add(btnOrdenar);

        panel.add(btnPanel, BorderLayout.SOUTH);

        add(panel);
        setVisible(true);
    }

    private void insertarDato() {
        try {
            int dato = Integer.parseInt(txtDato.getText());
            listaCircular.insertar(dato);
            actualizarTextArea();
            txtDato.setText("");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(null, "Por favor, ingrese un número válido.");
        }
    }

    private void actualizarTextArea() {
        txtArea.setText(listaCircular.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new ListaCircularInterfaz();
            }
        });
    }
}

class Nodo {
    public int dato;
    public Nodo siguiente;

    public Nodo(int dato) {
        this.dato = dato;
        this.siguiente = null;
    }
}

class ListaCircular {
    private Nodo cabeza;

    public ListaCircular() {
        this.cabeza = null;
    }

    public void insertar(int dato) {
        Nodo nuevo = new Nodo(dato);
        if (cabeza == null) {
            cabeza = nuevo;
            nuevo.siguiente = cabeza;
        } else {
            Nodo actual = cabeza;
            while (actual.siguiente != cabeza) {
                actual = actual.siguiente;
            }
            actual.siguiente = nuevo;
            nuevo.siguiente = cabeza;
        }
    }

    public void ordenar() {
        if (cabeza == null || cabeza.siguiente == cabeza) {
            return;
        }
        Nodo actual = cabeza;
        do {
            Nodo siguiente = actual.siguiente;
            while (siguiente != cabeza) {
                if (actual.dato > siguiente.dato) {
                    int temp = actual.dato;
                    actual.dato = siguiente.dato;
                    siguiente.dato = temp;
                }
                siguiente = siguiente.siguiente;
            }
            actual = actual.siguiente;
        } while (actual != cabeza);
    }

    @Override
    public String toString() {
        if (cabeza == null)
            return "Lista vacía";

        StringBuilder sb = new StringBuilder();
        Nodo actual = cabeza;
        do {
            sb.append(actual.dato).append(" ");
            actual = actual.siguiente;
        } while (actual != cabeza);
        return sb.toString();
    }
}
