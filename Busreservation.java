import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

		class login{
				JFrame fr;
				JTextField username;
				JTextField Password;
				JLabel user;
				JLabel pass;
				JButton loginbtn;
				JButton regbtn;
				JLabel title;
				JLabel sub;
				JPanel form;
				
				
			login(){
					fr=new JFrame("bus reservation system ");
					title=new JLabel("Bus Reservation System");
					sub=new JLabel("LOGIN");
					
					username=new JTextField(15);
					Password=new JTextField(15);
					
					user=new JLabel("username :");
					pass=new JLabel("Pasword:");
					
					
					loginbtn=new JButton("login");
					regbtn=new JButton("Register");
					form=new JPanel();
					form.setLayout(new GridLayout (3,2,10,10));
					
				form.add(user);
				form.add(username);
				
				form.add(pass);
				form.add(Password);
				
				form.add(loginbtn);
				form.add(regbtn);
	
					fr.setSize(400,300);
					fr.setLayout(new FlowLayout());
					
					fr.add(title);
					fr.add(sub);
					fr.add(form);
					
					loginbtn.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							
							String u=username.getText();
							String p=Password.getText();
							
							Db db=new Db();
							if (db.checkUser(u,p)){
								JOptionPane.showMessageDialog(fr,"login sucessfull");
								new Dash(u);
								fr.dispose();
							}else{
								JOptionPane.showMessageDialog(fr,"invalid username or password");
							}
						}
					});
					regbtn.addActionListener(new ActionListener(){
						public void actionPerformed(ActionEvent e){
							new reg();
						}
					});
				

				
					
					fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					
					fr.setVisible(true);
					
			}
		}
			
			
				
				
				
			public class Busreservation{
					public static void main(String[] args){
					login lo=new login();
					}
			}