import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
	class reg{
			JFrame fr;
			JLabel title;
			JLabel user;
			JLabel pass;
			JLabel confrim;
			JTextField username;
			JPasswordField password;
			JPasswordField confrimpass;
			JButton registrationbtn;
			
			
			reg(){
					fr=new JFrame ("User Registration");
					title =new JLabel("user registration");
					user=new JLabel("username");
					pass=new JLabel("password");
					confrim=new JLabel ("confrim Password");
					
					
					username=new JTextField(15);
					password=new JPasswordField(15);
					confrimpass=new JPasswordField(15);
					
					
					registrationbtn =new JButton("register");
					JPanel form = new JPanel();
					form.setLayout(new GridLayout (4,2,10,10));
					
					form.add(user);
					form.add(username);
					
					form.add(pass);
					form.add(password);
					
					form.add(confrim);
					form.add(confrimpass);
					
					form.add(new JLabel(""));
					form.add(registrationbtn);
					
					fr.setSize(400,300);
					fr.setLayout(new FlowLayout());
					
					fr.add(title);
					fr.add(form);
			registrationbtn.addActionListener(new RegisterAction(this));
					fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			fr.setVisible(true);
			}
			void registerUser(){
						String u = username.getText();
						String p= new String(password .getPassword());
						String cp=new String(confrimpass.getPassword());
						
						
						if(u.isEmpty() || p.isEmpty() || cp.isEmpty()){
							JOptionPane.showMessageDialog(fr,"pls fill all fields");
							return ;
						}
						if (!p.equals(cp)){
							JOptionPane.showMessageDialog(fr,"Password do not match");
							return;
						}Db db =new Db();
						db.adduser(u,p);
						JOptionPane.showMessageDialog(fr,"Registration succesful");
			}
	}
	class RegisterAction implements ActionListener{
		reg r;
		RegisterAction(reg r){
			this.r=r;
		}
		public void actionPerformed(ActionEvent e){
			r.registerUser();
		}
	}
	
					public class Register{
						public static void main(String[] args){
							reg r=new reg();
						}
					}
	