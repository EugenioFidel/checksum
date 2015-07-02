import java.awt.Color;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileInputStream;
import java.security.MessageDigest;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;



public class Checksumeitor extends JFrame {
	//esta constante sirve para saber si lo que hay en jlChecksum es un código o un mensaje
	static int LONGCHECKSUM=40;
	//fuentes grandotas para los resultados
	Font correcto=new Font("Verdana",Font.BOLD,20);	
	//un array de Checksums para almacenarlos
	private static final long serialVersionUID = 1L;
	
	//Dos botones, uno para comprobar y otro para borrar
	JButton jbSalir=new JButton("Salir");
	
	//Un JLabel para informar de los resultados. Vacío se rellenará en tiempo de ejecución
	JLabel jlResultado=new JLabel("Generador de Checksum SHA-1");
	
	//el GridBagLayout y el GridBagConstraints
	GridBagLayout gbl=new GridBagLayout();
	GridBagConstraints gbc=new GridBagConstraints();
	
	//un JPanel para los botones
	JPanel jpBotonero=new JPanel();
	
	//un JPanel para el código
	JLabel jlCodigo=new JLabel("Código documento");
	JTextField jtfCodigo=new JTextField();
	JPanel jpCodigo=new JPanel();
	GridLayout glCodigo=new GridLayout(0,1);
	
	
	//Un JMenuBar para los menus
	JMenuBar jmbMenu=new JMenuBar();
	//Un JMenu para Ayuda
	JMenu jmAyuda=new JMenu("Ayuda");
	//Un JMenu para Acerca	
	JMenuItem jmiAcerca=new JMenuItem ("Acerca de...");
	//Un JMenu para Acerca	
	JMenuItem jmiInstruc=new JMenuItem ("¿Cómo funciona?");
		
	
	//un GridLayout para el panel botonero
	GridLayout gl=new GridLayout(0,1);	
		
	//constructor
	Checksumeitor(){
	
		jlResultado.setForeground(Color.red);
		setSize(700, 440);	
		
		ActionListener eventoJMIInstrucciones = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	JOptionPane.showMessageDialog(jpBotonero,"Seleccione el archivo del que\nnecesite obtener el código\ny pulse abrir","Ayuda",JOptionPane.INFORMATION_MESSAGE);
		      }			
		    };
		jmiInstruc.addActionListener(eventoJMIInstrucciones);
		
		jmAyuda.add(jmiInstruc);
		jmAyuda.add(jmiAcerca);
		jmbMenu.add(jmAyuda);
		this.setJMenuBar(jmbMenu);
		
		//metodo de salida
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		//Establecemos el Layout
		setLayout(gbl);
		setTitle("Generador de códigos Checksum");
		// pongo la fuente correcto al JLabel
		jlResultado.setFont(correcto);
		
		//añado los botones al Botonero
		gl.setVgap(10);
		jpBotonero.add(jbSalir);
		jpBotonero.setLayout(gl);
		
		//los controles para el código
		jpCodigo.setLayout(glCodigo);
		jpCodigo.add(jlCodigo);
		jpCodigo.add(jtfCodigo);
		
		//el icono
		ImageIcon icono = new ImageIcon("./escudoEcylIco.jpg");
		setIconImage(icono.getImage());
		
		//JFileChooser para seleccionar el fichero
		JFileChooser jfcFichero=new JFileChooser();
		//el JFileChooser debe permitir seleccionar carpetas
		jfcFichero.setFileSelectionMode(JFileChooser.FILES_ONLY);
		//creamos el action listener que vamos posteriormente a añadir al boton Abrir del JFileCooser
		ActionListener eventoJFC = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		    	String checksum,fich;
		    	//extraemos el JFileChooser para actuar sobre él de forma local
		        JFileChooser theFileChooser = (JFileChooser) actionEvent.getSource();
		        //definimos la acción en el caso de haber pulsado abrir o cerrar
		        String command = actionEvent.getActionCommand();
		        //hemos pulsado abrir
		        if (command.equals(JFileChooser.APPROVE_SELECTION)) {
		          java.io.File selectedFile = theFileChooser.getSelectedFile();
		          try {
		        	jlResultado.setForeground(Color.GREEN); 
		        	//si hemos seleccionado un directorio hay que recorrerlo de manera recursiva
		        	//para acceder a todos sus subdirectorios
		        	
		        		fich=selectedFile.getAbsolutePath();
		        		checksum=calculadoraChecksum(fich);
		        		jlResultado.setText(checksum);
		        		
		        	
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//EscribirCierreHtml("./listaChecksums.html");
		        } else if (command.equals(JFileChooser.CANCEL_SELECTION)) {
		          System.out.println(JFileChooser.CANCEL_SELECTION);
		          //Cerramos la aplicación
		          System.exit(0);		          
		        }
		      }			
		    };
		jfcFichero.addActionListener(eventoJFC);
		
		ActionListener eventoSalir = new ActionListener() {
		      public void actionPerformed(ActionEvent actionEvent) {
		        System.out.println("salir");		        
		        System.exit(0);
		      }
		    };
		jbSalir.addActionListener(eventoSalir);
		
		//coloco los controles
		//JFileChooser jfcFichero
		gbc.gridx=0;
		gbc.gridy=0;
		gbc.gridwidth=1;
		gbc.weightx=1;
		gbc.fill=GridBagConstraints.BOTH;
		gbc.anchor=GridBagConstraints.CENTER;
		add(jfcFichero,gbc);
		
		//JLabel con el valor checksum
		//JFileChooser jfcFichero
		gbc.gridx=0;
		gbc.gridy=1;
		gbc.gridwidth=1;
		gbc.weightx=1;
		add(jlResultado,gbc);
		
		
		
		//Botones de gestión del formulario
		gbc.gridx=0;
		gbc.gridy=2;
		gbc.gridwidth=1;
		gbc.weightx=1;
		gbc.fill=GridBagConstraints.NONE;
		gbc.insets=new Insets(5,5,5,5);
		add(jpBotonero,gbc);		
	}

	private String calculadoraChecksum(String f) throws Exception{
		MessageDigest messageDigest = MessageDigest.getInstance("SHA1");		
		FileInputStream fileInput = new FileInputStream(f);
		byte[] dataBytes = new byte[1024];
		int bytesRead = 0;
		while ((bytesRead = fileInput.read(dataBytes)) != -1) {
			messageDigest.update(dataBytes, 0, bytesRead);
		}
		byte[] digestBytes = messageDigest.digest();
		StringBuffer sb = new StringBuffer("");		
		for (int i = 0; i < digestBytes.length; i++) {
			sb.append(Integer.toString((digestBytes[i] & 0xff) + 0x100, 16).substring(1));
		}
		System.out.println("Checksum for the File: " + sb.toString());		
		fileInput.close();
		return sb.toString();
	}	
}
