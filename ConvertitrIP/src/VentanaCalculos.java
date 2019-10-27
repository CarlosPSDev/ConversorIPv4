import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Toolkit;
import javax.swing.JTextField;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Dimension;

public class VentanaCalculos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textIntro;
	private JComboBox<String> comboBox;
	private JButton btnCalcular;
	private JLabel lblResultado;
	private JTextField textField;
	private JLabel lblBit;
	private JLabel lblError;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					VentanaCalculos frame = new VentanaCalculos();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public VentanaCalculos() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);		
		setPreferredSize(new Dimension(570, 487)); 		
		Dimension pantalla = Toolkit.getDefaultToolkit().getScreenSize();		
		Dimension ventana = this.getPreferredSize();		
		setLocation((pantalla.width - ventana.width) / 2, (pantalla.height - ventana.height) / 2);		
		pack();		
		
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JLabel lblNewLabel = new JLabel("Introduce el parámetro de entrada");
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblNewLabel.setBounds(55, 22, 276, 14);
		contentPane.add(lblNewLabel);

		JLabel lblseparadosPorPuntos = new JLabel("(separados por puntos cada 8 bits)");
		lblseparadosPorPuntos.setBounds(319, 22, 225, 14);
		contentPane.add(lblseparadosPorPuntos);

		textIntro = new JTextField();
		textIntro.setBounds(70, 62, 395, 27);
		contentPane.add(textIntro);
		textIntro.setColumns(10);

		JLabel lblSeleccionaOpcinA = new JLabel("Selecciona opción a ejecutar:");
		lblSeleccionaOpcinA.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblSeleccionaOpcinA.setBounds(61, 121, 202, 14);
		contentPane.add(lblSeleccionaOpcinA);

		comboBox = new JComboBox<String>();
		comboBox.setModel(new DefaultComboBoxModel<String>(new String[] { "Mostrar Máscara en Binario",
				"Convertir Decimal a Binario", "Convertir Binario a Decimal" }));
		comboBox.setBounds(273, 119, 202, 20);
		contentPane.add(comboBox);

		btnCalcular = new JButton("Calcular");

		btnCalcular.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent ev) {
				lblBit.setText("");
				textField.setText("");
				lblError.setText("");
				
				String[] tokens = textIntro.getText().split("\\.");
				int tokn = 0;
				String tokSt = "";
				String cadena = "";
				int dif = 0;
				String clase = "";
				
				try {
					if (comboBox.getSelectedIndex() == 0 | comboBox.getSelectedIndex() == 1) {// Convertir la máscara(0)
																								// o la IP(1) a binario
																								// con sus variaciones
						for (int j = 0; j < tokens.length; j++) {
							//procesar cada bloque de 8 dígitos y convertir a binario
							
							tokn = Integer.parseInt(tokens[j]);
							tokSt = Integer.toBinaryString(tokn) + "";
							if (tokSt.length() < 8) { // Si el nº es menor de 8 dígitos hay que rellenar con '0'
								dif = 8 - tokSt.length();
								for (int i = 0; i < dif; i++) {
									tokSt = "0" + tokSt;
								}
							}
							cadena = cadena + tokSt;
							if (j < tokens.length - 1)
								cadena = cadena + "."; // añadir el punto tras cada bloque de 8 dígitos
						}
						
						textField.setText(cadena);
						if (comboBox.getSelectedIndex() == 0) {
							// Si es la opción Máscara calcular cuántos bits (nº de '1' seguidos desde la izqda) tiene							

							int bits = 0;							
							for (int i = 0; i < cadena.length(); i++) {								
								if (cadena.charAt(i) == '1') {
									bits++;
								} else if (cadena.charAt(i) == '0') {									
									i = cadena.indexOf(".", i);									
									if (i == -1)
										i = cadena.length();
									
								}
							}
							lblBit.setText("Total de bits de máscara: " + bits);
						} else { // Si es la opción IP ver de qué clase es según cómo comienza
							if (cadena.charAt(0) == '0') {
								clase = "La red es de clase A";
							} else if (cadena.charAt(0) == '1' & cadena.charAt(1) == '0') {
								clase = "La red es de clase B";
							} else if (cadena.charAt(0) == '1' & cadena.charAt(1) == '1') {
								clase = "La red es de clase C";
							}
							lblBit.setText(clase);
						}
					} else if (comboBox.getSelectedIndex() == 2) { // Opción de convertir de binario a decimal
						for (int i = 0; i < tokens.length; i++) {
							
							tokSt = tokens[i];
							tokn = Integer.parseInt(tokSt, 2);
							cadena = cadena + tokSt;
							if (i < tokens.length - 1)
								cadena = tokn + ".";
						}
						
						textField.setText(cadena);
					}
				} catch (NumberFormatException e) {
					lblError.setText("Upss algún dato no es correcto");
				}
			}
		});
		btnCalcular.setFont(new Font("Tahoma", Font.PLAIN, 13));
		btnCalcular.setBounds(222, 190, 109, 23);
		contentPane.add(btnCalcular);

		lblResultado = new JLabel("Resultado");
		lblResultado.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblResultado.setBounds(81, 257, 159, 20);
		contentPane.add(lblResultado);

		textField = new JTextField();
		textField.setBounds(81, 302, 411, 20);
		contentPane.add(textField);
		textField.setColumns(10);

		lblBit = new JLabel("");
		lblBit.setBounds(134, 333, 331, 20);
		contentPane.add(lblBit);

		lblError = new JLabel("");
		lblError.setForeground(Color.RED);
		lblError.setFont(new Font("Tahoma", Font.BOLD, 12));
		lblError.setBounds(170, 377, 231, 14);
		contentPane.add(lblError);

		JLabel lblBy = new JLabel("By Carlos Pardos");
		lblBy.setForeground(new Color(204, 153, 0));
		lblBy.setFont(new Font("Vivaldi", Font.PLAIN, 14));
		lblBy.setBounds(39, 418, 117, 20);
		contentPane.add(lblBy);
	}
}
