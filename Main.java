import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Main {
    public static void main(String[] args) {
        CadastroUsuario cadastro = new CadastroUsuario();

        JFrame frame = new JFrame("Formulário");
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        JLabel labelNome = new JLabel("Nome:");
        JLabel labelEmail = new JLabel("Email:");
        JLabel labelIdade = new JLabel("Idade:");

        JTextField campoNome = new JTextField(16);
        JTextField campoEmail = new JTextField(16);
        JTextField campoIdade = new JTextField(16);

        JButton botaoCadastro = new JButton("Cadastrar");

        botaoCadastro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                cadastro.CadastrarUsuario(campoNome.getText(), campoEmail.getText(), toInt(campoIdade.getText()));
            }
        });

        panel.add(labelNome);
        panel.add(campoNome);

        panel.add(Box.createVerticalStrut(10));

        panel.add(labelEmail);
        panel.add(campoEmail);
        
        panel.add(Box.createVerticalStrut(10));

        panel.add(labelIdade);
        panel.add(campoIdade);

        panel.add(Box.createVerticalStrut(10));

        panel.add(botaoCadastro);
        
        frame.getContentPane().add(panel);
        frame.setVisible(true);
    }
}
