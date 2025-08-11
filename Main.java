import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.regex.Pattern;

public class Main {
    // Helper object responsible for keeping track and 
    static CadastroUsuario cadastro = new CadastroUsuario();
    
    // Objetos globais dentro do escopo dessa classe
    static JPanel lista_usuario_panel;

    static boolean validarEmail(String email) {
        String regex = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@" 
            + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";
        return Pattern.compile(regex)
            .matcher(email)
            .matches();
    }

    // FUnções helper pra não deixar muito código corrido no main()
    static JPanel create_register_panel() {
        // Esse é o painel do cadastro
        JPanel registerPanel = new JPanel();    
        registerPanel.setLayout(new BoxLayout(registerPanel, BoxLayout.Y_AXIS));

        // Os labels para cada campo, identificadores e de erro
        JLabel labelNome = new JLabel("Nome:");
        JLabel erroNomeObrigatorio = new JLabel("Nome obrigatório");
        erroNomeObrigatorio.setForeground(Color.RED);

        erroNomeObrigatorio.setVisible(false);

        JLabel labelEmail = new JLabel("Email:");
        JLabel erroEmailObrigatorio = new JLabel("Email obrigatório");
        JLabel erroEmailInvalido = new JLabel("Email inválido");
        
        erroEmailObrigatorio.setForeground(Color.RED);
        erroEmailInvalido.setForeground(Color.RED);

        erroEmailObrigatorio.setVisible(false);
        erroEmailInvalido.setVisible(false);

        JLabel labelIdade = new JLabel("Idade:");
        JLabel erroIdadeObrigatorio = new JLabel("Idade obrigatória");
        JLabel erroIdadeInvalido = new JLabel("Idade inválida");
        
        erroIdadeInvalido.setForeground(Color.RED);
        erroIdadeObrigatorio.setForeground(Color.RED);

        erroIdadeInvalido.setVisible(false);
        erroIdadeObrigatorio.setVisible(false);

        // Os campos finalmente
        JTextField campoNome = new JTextField(12);
        JTextField campoEmail = new JTextField(12);
        JTextField campoIdade = new JTextField(4);
        
        // Painel pra ser pai dos botoes e colocar um do lado do outro
        JPanel botaoPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        JButton botaoCadastro = new JButton("Cadastrar");
        JButton botaoLimpar = new JButton("Limpar");

        botaoPanel.add(botaoCadastro);
        botaoPanel.add(botaoLimpar);

        // Função de callback pro botao de cadastro
        botaoCadastro.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Cadastrando");
                boolean sucesso = true;
                
                // Verifica se o campo do nome está vazio
                if (campoNome.getText().equals("")) {
                    erroNomeObrigatorio.setVisible(true);
                    sucesso = false;
                } else {

                }

                // Verifica se o campo da idade está vazio
                if (campoIdade.getText().equals("")) {
                    erroIdadeObrigatorio.setVisible(true);
                    sucesso = false;
                } else {
                    // Verifica se a idade é realmente um numero
                    try {
                        Integer.parseInt(campoIdade.getText());
                        erroIdadeInvalido.setVisible(false); // Se tiver dado certo, aproveita e tira o erro
                    } catch (NumberFormatException ex) {
                        erroIdadeInvalido.setVisible(true);
                        sucesso = false;
                    } 
                }

                // Verifica se o campo do email está vazio
                if (campoEmail.getText().equals("")) {
                    erroEmailObrigatorio.setVisible(true);
                    sucesso = false;

                // Se tiver algo no campo, verifica se o email é valido
                } else if (validarEmail(campoEmail.getText()) == false) {
                    erroEmailInvalido.setVisible(true);
                    sucesso = false;
                }
                
                // Se tiver dado erro em qualquer uma das verificações, nao continua o processo de cadastro
                if (!sucesso) {
                    return;
                }

                erroNomeObrigatorio.setVisible(false);
                erroEmailObrigatorio.setVisible(false);
                erroEmailInvalido.setVisible(false);
                erroIdadeInvalido.setVisible(false);
                erroIdadeObrigatorio.setVisible(false);

                cadastro.CadastrarUsuario(campoNome.getText(), campoEmail.getText(), Integer.parseInt(campoIdade.getText()));

                JOptionPane.showMessageDialog(
                    null,            
                    "Cadastro realizado com sucesso!\n" + "Nome: " + campoNome.getText() + "\nEmail: " + campoEmail.getText() + "\nIdade: " + campoIdade.getText() + " anos", 
                    "Sucesso",                   
                    JOptionPane.INFORMATION_MESSAGE 
                );
            }
        });

        // Função de callback pra limpar os campos
        botaoLimpar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                System.out.println("Limpando");
                campoNome.setText("");
                campoEmail.setText(""); 
                campoIdade.setText("");
            }
        });

        // Adicionando os elementos no painel
        registerPanel.add(labelNome);
        registerPanel.add(campoNome);
        registerPanel.add(erroNomeObrigatorio);

        registerPanel.add(Box.createVerticalStrut(10));

        registerPanel.add(labelEmail);
        registerPanel.add(campoEmail);
        registerPanel.add(erroEmailObrigatorio);
        registerPanel.add(erroEmailInvalido);
        
        registerPanel.add(Box.createVerticalStrut(10));

        registerPanel.add(labelIdade);
        registerPanel.add(campoIdade);
        registerPanel.add(erroIdadeObrigatorio);
        registerPanel.add(erroIdadeInvalido);

        registerPanel.add(Box.createVerticalStrut(10));

        registerPanel.add(botaoPanel);

        return registerPanel;
    } 

    static JPanel create_dashboard_panel() {
        JPanel dashboard_panel = new JPanel();
        dashboard_panel.setLayout(new BoxLayout(dashboard_panel, BoxLayout.Y_AXIS));
        
        lista_usuario_panel = new JPanel();
        lista_usuario_panel.setLayout(new BoxLayout(lista_usuario_panel, BoxLayout.Y_AXIS));

        JLabel user_count = new JLabel("Usuários Cadastrados: ");

        dashboard_panel.add(user_count);
        dashboard_panel.add(lista_usuario_panel);

        return dashboard_panel;
    }

    static void update_user_list() {
        lista_usuario_panel.removeAll();
        for (Usuario u : cadastro.usuarios_registrados) {
            JLabel label = new JLabel(u.nome + " - " + u.email + " - " + u.idade + " anos");
            lista_usuario_panel.add(label);
        }
        lista_usuario_panel.revalidate();
        lista_usuario_panel.repaint();
    }

    static JTabbedPane create_tabbed_pane() {
        JTabbedPane tabs = new JTabbedPane();
        
        tabs.addChangeListener(e -> {
            if (tabs.getSelectedIndex() == 1) { // Index 1 = Dashboard
                update_user_list();
            }
        });

        return tabs;
    }

    public static void main(String[] args) {
        // Frame principal do formulário
        JFrame frame = new JFrame("Formulário");
        frame.setSize(400, 300);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new FlowLayout());

        // Separação por abas
        JTabbedPane tabs = create_tabbed_pane();

        // Paineis contém os items, tipo botão e campo
        // Esse é o painel do cadastro
        JPanel register_panel = create_register_panel();

        // Painel da visualização
        JPanel dashboard_panel = create_dashboard_panel();

        // Registrar os paineis nas abas
        tabs.addTab("Registro", register_panel);
        tabs.addTab("Dashboard", dashboard_panel);
        
        frame.getContentPane().add(tabs);
        frame.setVisible(true);
    }
}
