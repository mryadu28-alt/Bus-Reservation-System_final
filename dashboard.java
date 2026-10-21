import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;



import java.io.File;
import java.util.Scanner;
import java.awt.BorderLayout;
class Base{
			JFrame fr;
			Base(String title){
					fr=new JFrame(title);
					fr.setSize(500,400);
					fr.setLayout(new FlowLayout());
			}
}
	class Dash extends Base {
			String username;
			JLabel title;
			JButton book;
	
			JButton tickets;
			JButton logout;
			Dash(String username){
						super ("bus resevation System");
						this.username=username;
						JPanel header=new JPanel(new FlowLayout(FlowLayout.LEFT,15,12));
						header.setBackground(new Color(20,48,85));
						title =new JLabel("bus reservation system ");
						title.setForeground(Color.WHITE);
						title.setFont(new Font("Arial",Font.BOLD,18));
							book=new JButton("book tickets");
							
							tickets=new JButton("my tickets");
							logout=new JButton("Logout");
							
							header.add(title);
							header.add(book);
							header.add(tickets);
JPanel logbttn=new JPanel(new FlowLayout(FlowLayout.LEFT,15,12));
						logbttn.setBackground(new Color(20,48,85));
						logbttn.add(logout);
						
						header.add(logbttn);
					fr.setSize(1000,700);
					fr.setExtendedState(JFrame.MAXIMIZED_BOTH);
fr.setLayout(new BorderLayout());
					fr.setLocationRelativeTo(null);
					

						fr.add(header,BorderLayout.NORTH);
							
																
							book.addActionListener(new ActionListener(){
								public void actionPerformed( ActionEvent e){
									   new Bookticket(username);
								}
							});
							tickets.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        new mytickets(username);
    }
});
logout.addActionListener(new ActionListener(){
	public void actionPerformed(ActionEvent e){
		fr.dispose();
		new login();
	}
});
							fr.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
								fr.setVisible(true);
			}
			}
			

class mytickets extends Base {
    String username;
	mytickets(String username){
		super("mytickets");
		this.username=username;
		fr.setSize(500,400);
		JTextArea area=new JTextArea();
		area.setEditable(false);
		boolean found=false;
		try{
		Scanner sc = new Scanner(new File("bookings.txt"));
			while(sc.hasNextLine()){
				String line=sc.nextLine();
				if (line.trim().equalsIgnoreCase("Username: " + username.trim())) {
					found=true;
					area.append(line+"\n");
					while(sc.hasNextLine()){
						line=sc.nextLine();
						if(line.startsWith("--")){
							area.append("-----------------------------------------------\n");
							break;
						}area.append(line+"\n");
					}
				}
			}
			sc.close();
			if (!found){
				area.setText("NO tickets bookin yet");
			}
		}catch(Exception e){
			 e.printStackTrace();
    area.setText("Error: " + e.getMessage());
				area.setText("no tickets found");
			}
			
			JScrollPane scroll = new JScrollPane(area);

scroll.setVerticalScrollBarPolicy(
    JScrollPane.VERTICAL_SCROLLBAR_ALWAYS
);

fr.setLayout(new BorderLayout());
fr.add(scroll, BorderLayout.CENTER);
			fr.setLocationRelativeTo(null);
			fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
			fr.setVisible(true);
		}
	}
			
			
						

	
							class Bookticket extends Base {
	String username;
    JLabel title;
    JLabel from;
    JLabel to;
    JTextField fromText;
    JTextField totext;
    JButton search;
	static String busName;
static String source;
static String destination;

							
					
    Bookticket(String username) {
        super("Book Ticket");
		this.username=username;
        fr.setSize(600, 500);
        fr.setLayout(new BorderLayout(10, 10));

        JPanel top = new JPanel(new FlowLayout());

        title = new JLabel("Book Ticket");
        from = new JLabel("From");
        to = new JLabel("To");

        fromText = new JTextField(10);
        totext = new JTextField(10);
        search = new JButton("Search");

        top.add(title);
        top.add(from);
        top.add(fromText);
        top.add(to);
        top.add(totext);
        top.add(search);

        fr.add(top, BorderLayout.NORTH);

        JPanel busPanel = new JPanel();
        busPanel.setLayout(new BoxLayout(busPanel,BoxLayout.Y_AXIS));
		JScrollPane scrollPane=new JScrollPane(busPanel);
        fr.add(scrollPane, BorderLayout.CENTER);

        search.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                

                String f = fromText.getText();
                String t = totext.getText();
				busPanel.removeAll();
                Db db = new Db();
                String result = db.searchbus(f, t);
				try{
					db.con.close();
				}catch(Exception ex){
					System.out.println(e);
				}
				

                if (result == null || result.trim().isEmpty()) {
                    JOptionPane.showMessageDialog(fr, "No buses found");
                } else {
					String[] buses=result.split("\n\n");
					for(int i=0;i<buses.length;i++){
						final String bus=buses[i];
						

                    JPanel row = new JPanel(new BorderLayout(10,10));

                    JTextArea details = new JTextArea(bus);
                    details.setEditable(false);
					details.setLineWrap(true);
					details.setWrapStyleWord(true);
                    JButton book = new JButton("Book Ticket");

                    row.add(details, BorderLayout.CENTER);
                    row.add(book, BorderLayout.EAST);
					row.setBorder(
					BorderFactory.createEmptyBorder(10,10,10,10)
					);
                    busPanel.add(row);

                    book.addActionListener(new ActionListener() {
                        public void actionPerformed(ActionEvent e) {

                          Bookticket.busName = bus.split("\n")[0].replace("Bus: ", "").trim();
						  
Bookticket.source = f;
Bookticket.destination = t;
String busType =Bookticket.busName.toLowerCase();

if (busType.contains("sleeper")) {
    new sleeperbus(username);

}else if(busType.contains("ksrtc")){
	new KSRTC(username);
}else if(busType.contains("swift")){
	new swift(username);
}else{
	
    new normalBus(username);
}
                        }
                    });
                }
				
			}			

                busPanel.revalidate();
                busPanel.repaint();
            }
        });

        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
	}
							}
						
							
	class seats extends Base{
					JButton seat1;
					JButton seat2;
					JButton seat3;
					JButton seat4;
					
					seats(){
							super("select seat");
							JLabel title =new JLabel("select uour seat");
							seat1=new JButton("1");
							seat2=new JButton("2");
							seat3=new JButton("3");
							seat4=new JButton("4");
							
							fr.add(title);
							fr.add(seat1);
							fr.add(seat2);
							fr.add(seat3);
							fr.add(seat4);
							
							fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							fr.setVisible(true);
	}
	}
					
					class seatLayout extends Base{
						seatLayout(String title){
						super  (title);
					}
					}
								
		
						
				
class normalBus extends seatLayout {
	JButton[] seats =new JButton[40];
	

    normalBus(String username) {
        super("Normal Bus - Select Seat");

        fr.setSize(700, 700);
        fr.setResizable(false);
        fr.setLayout(new BorderLayout(10, 10));

        JLabel driver = new JLabel("          DRIVER", JLabel.CENTER);
        fr.add(driver, BorderLayout.NORTH);

        JPanel seatPanel = new JPanel();
        seatPanel.setLayout(new GridLayout(10,2,8,8));

        for (int i = 0; i < 40; i++) {
            if ((i+1) % 4 == 3) {
                seatPanel.add(new JLabel(""));
            }
				seats[i]=new JButton(String.valueOf(i+1));
				seats[i].setBackground(Color.CYAN);
				seats[i].setOpaque(true);
				seats[i].setFocusPainted(false);
				seats[i].addActionListener(new ActionListener(){
					public  void actionPerformed(ActionEvent e){
								JButton seat =(JButton)e.getSource();
								
								if(seat.getBackground().equals(Color.CYAN)){
									seat.setBackground(Color.GREEN);
								}else{seat.setBackground(Color.CYAN);
								}
					}
				});
				seatPanel.add(seats[i]);
				}
			
            loadBookedseats();
        fr.add(seatPanel, BorderLayout.CENTER);

        JButton confirm = new JButton("Confirm Seat");
        fr.add(confirm, BorderLayout.SOUTH);
		
		confirm.addActionListener(new ActionListener(){
							public void actionPerformed(ActionEvent e){
								
							int count =0;
							for (int i=0;i<40;i++){
										if (seats[i].getBackground().equals(Color.GREEN)){
											count++;
										}
							}
							if (count==0){
									JOptionPane.showMessageDialog(fr,"please select a seat");
							}else{
								
								String[] selectedSeats=new String[count];
								int j=0;
								for(int i=0;i< 40;i++){
									if(seats[i].getBackground().equals(Color.GREEN)){
											selectedSeats[j]=seats[i].getText();
											j++;
									
									
									
							}
								}
								new Passengerdetails(count, selectedSeats,
        Bookticket.busName, Bookticket.source, Bookticket.destination,username);
							}
							}
		});
								
										

        fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        fr.setLocationRelativeTo(null);
        fr.setVisible(true);
    }
	
void loadBookedseats(){
							try{
								Scanner sc =new Scanner(new File("bookings.txt"));
								String bus="";
								String from="";
								String to="";
				while(sc.hasNextLine()){
				String line=sc.nextLine().trim();
				if(line.startsWith("Bus:")){
						 bus =line.substring(4).trim();
				}else if(line.startsWith("From:")){
					from=line.substring(5).trim();
				}
				else if(line.startsWith("To:")){
					to=line.substring(3).trim();
				}else if(line.startsWith("Seat:")){
					if (bus.equalsIgnoreCase("Normal Bus")||bus.equalsIgnoreCase("Express Bus")||bus.equalsIgnoreCase("Private")&&from.equalsIgnoreCase(Bookticket.source)&&to.equalsIgnoreCase(Bookticket.destination)){
						
						
				    try {
                        int seatNo = Integer.parseInt(
                            line.substring(5).trim()
                        );

                        if (seatNo >= 1 && seatNo <= seats.length) {
                            seats[seatNo - 1].setBackground(Color.RED);
                            seats[seatNo - 1].setEnabled(false);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println("Invalid seat number");
                    }
                }
            }
        }

        sc.close();

		}catch(Exception e){
							System.out.println("could not load bookedd setas:"+e.getMessage());
						}
		}
}

class Passengerdetails {
				Passengerdetails(int count ,String[] selectedSeats,String bus,String from,String to,String username){
							JFrame frame =new JFrame("passengerdetalis");
							frame.setSize(450,500);
							frame.setLayout(new BorderLayout());
							JPanel panel = new JPanel();
							panel.setLayout(new GridLayout(0,2,5,5));
							JTextField[] names =new JTextField[count];
				             JTextField[] ages = new JTextField[count];
							 JTextField[] phones=new JTextField[count];
							 
							   int[] ageValues = new int[count];
							 for(int i=0;i< count;i++){
									panel.add(new JLabel("passenger "+(i + 1)));
									panel.add(new JLabel("seat:"+selectedSeats[i]));
									
									
									panel.add(new JLabel("Name:"));
									names[i]=new JTextField();
									panel.add(names[i]);
									
									
									
									panel.add(new JLabel("age:"));
									ages[i]=new JTextField();
									panel.add(ages[i]);
									


									panel.add(new JLabel("phone:"));
									phones[i]=new JTextField();
									panel.add(phones[i]);
							 }
							 
							 JButton confrim =new JButton ("confrim booking");
							 
							 confrim.addActionListener(new ActionListener(){
								 public void actionPerformed(ActionEvent e){
									 
									 for (int i =0;i<count ; i++){
										 if (names [i].getText().trim().isEmpty()||ages[i].getText().trim().isEmpty()||phones[i].getText().trim().isEmpty()){
											 JOptionPane.showMessageDialog(frame,"pls enter all detalils");
											 return;
										 }
											 try {
												 ageValues[i]=Integer.parseInt(ages[i].getText().trim());
												 if (ageValues[i]<=0){
													 JOptionPane.showMessageDialog(frame,"enter a valid age");
													 return;
												 }
												 }catch(NumberFormatException ex){
													 JOptionPane.showMessageDialog(frame,"Age must be a number");
													 return;
												 }
											 }
											 System.out.println("Bus name being saved: " + bus);
											 boolean saved = true;
											 for (int i=0;i<count;i++){
												if(!BookingFile.saveBooking( bus,
                        selectedSeats[i],
                        names[i].getText().trim(),
                        ageValues[i],
                        phones[i].getText().trim(),
                        from,
                        to,
						username)){
                   saved=false;
				   break;
						}
											 }
											 if(saved){
												
												 JOptionPane.showMessageDialog(frame,"Booking confrimed");
												 JFrame ticket=new JFrame("Bus Ticket");
												 ticket.setLayout(new GridLayout(0,1,5,5));
												 
												 ticket.add(new JLabel("BUS RESERAVTION TICKET"));
												 ticket.add(new JLabel("Passenger:"+names[0].getText()));
												 ticket.add(new JLabel("Bus:"+bus));
												 ticket.add(new JLabel("from:"+from));
												 ticket.add(new JLabel("TO:"+to));
												 ticket.add(new JLabel("seat:"+selectedSeats[0]));
												 ticket.setSize(350,300);
												 ticket.setLocationRelativeTo(null);
												 ticket.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
												 ticket.setVisible(true);
												 
												 
												 frame.dispose();
											 }else{
											 JOptionPane.showMessageDialog(frame,"Booking Failed");
											 }
											
									 
									
									
								 
									 }
								 });
							 frame.add(new JScrollPane(panel),BorderLayout.CENTER);
							 frame.add(confrim,BorderLayout.SOUTH);
							 frame.setLocationRelativeTo(null);
							 frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
							 frame.setVisible(true);
				}
}
										 
							
									
		class sleeperbus extends seatLayout{
			JButton[] lowerseats =new JButton[20];
			JButton[] upperseats =new JButton[20];
						sleeperbus(String username){
								super("sleeper bus ");
									fr.setSize (700 ,700);
									fr.setLayout(new BorderLayout(10,10));
									
									
									JPanel lower = new JPanel();
										lower.setLayout(new GridLayout(10,2,8,8));
												lower.setBorder(
													BorderFactory.createTitledBorder("lower berth")
													);
													
												for(int i=1;i<=20;i++){
														JButton berth =new JButton("U"+i);
														berth.setBackground(Color.CYAN);
															berth.setOpaque(true);
															lowerseats[i-1]=berth;
															berth.addActionListener(new ActionListener(){
																public void actionPerformed(ActionEvent e){
																	if (berth.getBackground().equals(Color.CYAN)){
																berth.setBackground(Color.GREEN);
															}else{
																berth.setBackground(Color.CYAN);
															}
																}
															});
								lower .add (berth);
												}
												
										JPanel upper = new JPanel();
										upper.setLayout(new  GridLayout(10,2,8,8));
										upper.setBorder(
													BorderFactory.createTitledBorder("uppper berth")
													);
														
												for(int i=1;i<=20;i++){
														JButton berth =new JButton("L"+i);
														berth.setBackground(Color.CYAN);
															berth.setOpaque(true);
															upperseats[i-1]=berth;
															
															berth.addActionListener(new ActionListener(){
																public void actionPerformed (ActionEvent e){
																	if(berth.getBackground().equals(Color.CYAN)){
																		
																berth.setBackground(Color.GREEN);
															}else{
																	berth.setBackground(Color.CYAN);
															}
																}
															});
															
															upper.add(berth);
												}
												JPanel berths =new JPanel(new GridLayout(1,2,10,10));
												berths.add(lower);
												berths.add(upper);
												fr.add (berths ,BorderLayout.CENTER);
												JButton confirm = new JButton("Confirm Seat");
							fr.add(confirm, BorderLayout.SOUTH);

							confirm.addActionListener(new ActionListener() {
    public void actionPerformed(ActionEvent e) {
        int count =0;
		for (int i=0;i<20;i++){
			if(lowerseats[i].getBackground().equals(Color.GREEN)){
								count++;
			}
			if(upperseats[i].getBackground().equals(Color.GREEN)){
					count++;
			}
		}
		if (count == 0) {
    JOptionPane.showMessageDialog(fr, "Please select a berth");
} else {
    String[] selectedSeats = new String[count];
    int j = 0;

    for (int i = 0; i < 20; i++) {
        if (lowerseats[i].getBackground().equals(Color.GREEN)) {
            selectedSeats[j] = lowerseats[i].getText();
            j++;
        }

        if (upperseats[i].getBackground().equals(Color.GREEN)) {
            selectedSeats[j] = upperseats[i].getText();
            j++;
        }
    }

    new Passengerdetails(count, selectedSeats,
        Bookticket.busName, Bookticket.source, Bookticket.destination,username);
}
				
    }
});
												fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
												fr.setLocationRelativeTo(null);
												fr.setVisible(true);
						}
												
			}
												
		
		class KSRTC extends seatLayout{
						JButton[] seats =new JButton[40];
						void loadBookedseats(){
							try{
								Scanner sc =new Scanner(new File("bookings.txt"));
								String bus="";
								String from="";
								String to="";
				while(sc.hasNextLine()){
				String line=sc.nextLine().trim();
				if(line.startsWith("Bus:")){
						 bus =line.substring(4).trim();
				}else if(line.startsWith("From:")){
					from=line.substring(5).trim();
				}
				else if(line.startsWith("To:")){
					to=line.substring(3).trim();
				}else if(line.startsWith("Seat:")){
					if (bus.equalsIgnoreCase("KSRTC")&&from.equalsIgnoreCase(Bookticket.source)&&to.equalsIgnoreCase(Bookticket.destination)){
						String seatText=line.substring(5).trim();
					
						
										try{
											int seatNo=Integer.parseInt(line.substring(5).trim());
											if(seatNo>=1&&seatNo<=40){
											seats[seatNo-1].setBackground(Color.RED);}
										}
										catch(NumberFormatException ex){
										}
									}
								}
							}
						
				
sc.close();
		}catch(Exception e){
							System.out.println("could not load bookedd setas:"+e.getMessage());
						}
		}
						KSRTC(String username){
								super("KSRTC-Select");
								fr.setSize(700,700);
								fr.setLayout(new BorderLayout(10,10));
								
								JLabel driver=new JLabel("DRIVER" ,JLabel.CENTER);
								fr.add(driver,BorderLayout.NORTH);
								
								JPanel seatPanel =new JPanel(new GridLayout(8,1,5,10));
								int n=0;
								for (int i=0;i<8;i++){
									JPanel row =new JPanel(new GridLayout(1,3,80,5));
									JPanel left=new JPanel(new GridLayout(1,2,5,5));
									JPanel right=new JPanel(new GridLayout(1,3,5,5));
									
									for(int j=0;j<5;j++){
										n++;
										seats[n-1]=new JButton(""+n);
										seats[n-1].setBackground(Color.CYAN);
										seats[n-1].setOpaque(true);
										
										
										seats[n-1].addActionListener(new ActionListener(){
								
    public void actionPerformed(ActionEvent e) {
        JButton seat = (JButton) e.getSource();

        if (seat.getBackground().equals(Color.RED)) {
            JOptionPane.showMessageDialog(fr,
                    "This seat is already booked!");
        } else if (seat.getBackground().equals(Color.CYAN)) {
            seat.setBackground(Color.GREEN);
        } else {
            seat.setBackground(Color.CYAN);
        }
    }
});
if(j<2){
		left.add(seats[n-1]);
}else{
	right.add(seats[n-1]);
}
									}
									row.add(left);
									row.add(right);
									seatPanel.add(row);
									
								}loadBookedseats();
								fr.add(new JScrollPane(seatPanel),BorderLayout.CENTER);
								JButton confrim=new JButton("confrim seat");
								fr.add(confrim,BorderLayout.SOUTH);
								confrim.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent e){
										int count=0;
										for(int i=0;i<40;i++){
											if (seats[i].getBackground().equals(Color.GREEN)){
												count++;
											}
										}
										if (count==0){
												JOptionPane.showMessageDialog(fr,"please select a seat");
												return;
										}
										String[] selectedSeats=new String[count];
										int j=0;
										for (int i=0;i<40;i++){
											if(seats[i].getBackground().equals(Color.GREEN)){
												selectedSeats[j++]=seats[i].getText();
											}
										}new Passengerdetails(count, selectedSeats, Bookticket.busName,
    Bookticket.source, Bookticket.destination, username);
								}});		
fr.setLocationRelativeTo(null);
fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						fr.setVisible(true);}
}						
													
								
									
									
		class swift extends seatLayout{
					JButton[] seats =new JButton[40];
					swift(String username){
						super("swift-Select seat");
									
										fr.setSize(700,700);
								fr.setLayout(new BorderLayout(10,10));
								
								JLabel driver=new JLabel("DRIVER" ,JLabel.CENTER);
								fr.add(driver,BorderLayout.NORTH);
								
								JPanel seatPanel =new JPanel(new GridLayout(8,1,5,10));
								int n=0;
								for (int i=0;i<8;i++){
									JPanel row =new JPanel(new GridLayout(1,3,80,5));
									JPanel left=new JPanel(new GridLayout(1,2,5,5));
									JPanel right=new JPanel(new GridLayout(1,3,5,5));
									
									for(int j=0;j<5;j++){
										n++;
										seats[n-1]=new JButton(""+n);
										seats[n-1].setBackground(Color.CYAN);
										seats[n-1].setOpaque(true);
										
										
										seats[n-1].addActionListener(new ActionListener(){
								
    public void actionPerformed(ActionEvent e) {
        JButton seat = (JButton) e.getSource();

        if (seat.getBackground().equals(Color.RED)) {
            JOptionPane.showMessageDialog(fr,
                    "This seat is already booked!");
        } else if (seat.getBackground().equals(Color.CYAN)) {
            seat.setBackground(Color.GREEN);
        } else {
            seat.setBackground(Color.CYAN);
        }
    }
});
if(j<2){
		left.add(seats[n-1]);
}else{
	right.add(seats[n-1]);
}
									}
									row.add(left);
									row.add(right);
									seatPanel.add(row);
									
								}loadBookedseats();
								fr.add(new JScrollPane(seatPanel),BorderLayout.CENTER);
								JButton confrim=new JButton("confrim seat");
								fr.add(confrim,BorderLayout.SOUTH);
								confrim.addActionListener(new ActionListener(){
									public void actionPerformed(ActionEvent e){
										int count=0;
										for(int i=0;i<40;i++){
											if (seats[i].getBackground().equals(Color.GREEN)){
												count++;
											}
										}
										if (count==0){
												JOptionPane.showMessageDialog(fr,"please select a seat");
												return;
										}
										String[] selectedSeats=new String[count];
										int j=0;
										for (int i=0;i<40;i++){
											if(seats[i].getBackground().equals(Color.GREEN)){
												selectedSeats[j++]=seats[i].getText();
											}
										}new Passengerdetails(count, selectedSeats, Bookticket.busName,
    Bookticket.source, Bookticket.destination, username);
								}});		
fr.setLocationRelativeTo(null);
fr.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
						fr.setVisible(true);}
						
	
										void loadBookedseats(){
try{
		Scanner sc =new Scanner(new File ("booking.txt"));
		while(sc.hasNextLine()){
			String line =sc.nextLine().trim();
			if(line.equals("Bus:Swift")){
				String seatLine=sc.nextLine().trim();
				int seatNO =Integer.parseInt(
								seatLine.substring(5).trim());
								
								
								if(seatNO>=1&&seatNO<=40){
									seats[seatNO-1].setBackground(Color.RED);
								}
			}
		}
		sc.close();
}catch(Exception e){
			System.out.println(e.getMessage());
			}
									
										}	
									
																		
		}						
	public class dashboard{
			public static void main(String[] args){
					Dash d=new Dash("testuser");
			
			}
	}			
	