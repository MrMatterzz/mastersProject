package com.PTO.appPackage;

import com.PTO.dao.*;
import com.PTO.domain.*;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.Color;
import java.awt.Font;
import java.awt.HeadlessException;
import java.awt.CardLayout;
import javax.swing.ListSelectionModel;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.awt.event.ActionEvent;
import java.awt.Desktop;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.JScrollPane;
import java.awt.Dimension;
import java.awt.Cursor;

public class mainWindow {

	private JFrame frame;
	private JComboBox<String> rtSrchField;
	private JLabel generalnfoLbl;
	private JLabel tBusLbl;
	private JLabel tBusCounterLbl;
	private JLabel activeRtsLbl;
	private JLabel rtCounterlbl;
	private JLabel activeBusLbl;
	private JComboBox<String> criteriaBox;
	private JLabel criterialbl;
	private JLabel busCounterLbl;
	private JButton srchBtn;
	private JButton detailsBtn;
	private JButton mapViewBtn;
	private JLabel srchLabel;
	private JButton routeEditBtn;
	private JButton changeRouteStatusBtn;
	private JTable activeRoutesTable;
	private JPanel generalInfo;
	private JPanel actionBtns;
	private JPanel adminBtns;
	private JPanel srchPanel;
	private JPanel routeEditPanel;
	private JPanel mainPage;
	private JPanel routeDetailsPanel;
	private JPanel titlePanel;
	private JTable routeInfo;
	private JButton btnStopChange;
	private JTable stopsInfo;
	private JPanel detailsPanel;
	private JLabel lblNewLabel_1;
	private JTable routeDetInfo;
	private JTable routeStopsInfo;
	private JButton btnStopMapView;
	private JButton btnRecallVehicle;
	private JButton btnSendVehicle;
	private JLabel activeTransportLbl;
	private JLabel activeTransportCount;
	private JLabel reserveTransportLbl;
	private JLabel reserveTransportCount;
	private JButton btnGoBack;
	private JLabel tramLbl;
	private JLabel tramCounterLbl;
	
	private RouteDAO routeDAO = new RouteDAOImpl();
	private RouteStopDAO routeStopDAO = new RouteStopDAOImpl();
	private TransportDAO transportDAO = new TransportDAOImpl();
	
	DefaultTableModel tableModelRoutes = new DefaultTableModel();
	DefaultTableModel tableModelRouteStops = new DefaultTableModel();
	DefaultTableModel tableModelSingleRoute = new DefaultTableModel();
	private JLabel lblNewLabel;
	private JButton btnAddStop;
	private JButton btnCancel;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JScrollPane scrollPane_2;
	private JScrollPane scrollPane_3;
	private JScrollPane scrollPane_4;
	
	//ComboBoxModel rtSrchFieldModel = new DefaultComboBoxModel();
	

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					mainWindow window = new mainWindow();
					window.frame.setVisible(true);
					UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
	
	public mainWindow() {
		initialize();
	}
	
	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {	
		int randTransportValue = (int) Math.floor(Math.random()*(60-40+1)+40);
		frame = new JFrame();
		frame.setResizable(false);
		frame.setTitle("Public Traffic Organizer");
		frame.setBounds(100, 100, 1440, 900);
		frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		frame.getContentPane().setLayout(new CardLayout(0, 0));
		
		mainPage = new JPanel();
		frame.getContentPane().add(mainPage, "mainPage");
		mainPage.setLayout(null);
		
		generalInfo = new JPanel();
		generalInfo.setBorder(new LineBorder(new Color(0, 0, 0)));
		generalInfo.setBounds(4, 11, 1406, 135);
		mainPage.add(generalInfo);
		generalInfo.setLayout(null);
		
		generalnfoLbl = new JLabel("Загальна Інформація");
		generalnfoLbl.setBounds(6, 16, 1394, 36);
		generalInfo.add(generalnfoLbl);
		generalnfoLbl.setFont(new Font("Tahoma", Font.PLAIN, 23));
		generalnfoLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		activeRtsLbl = new JLabel("Кількість активних маршрутів");
		activeRtsLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		activeRtsLbl.setBounds(283, 63, 200, 31);
		generalInfo.add(activeRtsLbl);
		activeRtsLbl.setHorizontalAlignment(SwingConstants.CENTER);
		activeRtsLbl.setLabelFor(rtCounterlbl);
		
		rtCounterlbl = new JLabel("counterLbl");
		rtCounterlbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		rtCounterlbl.setBounds(283, 102, 200, 31);
		generalInfo.add(rtCounterlbl);
		rtCounterlbl.setHorizontalAlignment(SwingConstants.CENTER);
		rtCounterlbl.setText(Integer.toString(routeDAO.getRoutesByStatus("Активний").size()));
		
		activeBusLbl = new JLabel("\u041A\u0456\u043B\u044C\u043A\u0456\u0441\u0442\u044C \u0430\u043A\u0442\u0438\u0432\u043D\u0438\u0445 \u0430\u0432\u0442\u043E\u0431\u0443\u0441\u0456\u0432");
		activeBusLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		activeBusLbl.setBounds(493, 63, 200, 31);
		generalInfo.add(activeBusLbl);
		activeBusLbl.setHorizontalAlignment(SwingConstants.CENTER);
		activeBusLbl.setLabelFor(busCounterLbl);
		
		busCounterLbl = new JLabel("bus counter");
		busCounterLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		busCounterLbl.setBounds(493, 102, 200, 31);
		busCounterLbl.setText(Integer.toString(randTransportValue));
		generalInfo.add(busCounterLbl);
		busCounterLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		tBusLbl = new JLabel("\u041A\u0456\u043B\u044C\u043A\u0456\u0441\u0442\u044C \u0430\u043A\u0442\u0438\u0432\u043D\u0438\u0445 \u0442\u0440\u043E\u043B\u0435\u0439\u0431\u0443\u0441\u0456\u0432");
		tBusLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tBusLbl.setBounds(703, 63, 200, 31);
		generalInfo.add(tBusLbl);
		tBusLbl.setHorizontalAlignment(SwingConstants.CENTER);
		tBusLbl.setLabelFor(tBusCounterLbl);
		
		tBusCounterLbl = new JLabel(" Trolleybus counter");
		tBusCounterLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tBusCounterLbl.setBounds(703, 102, 200, 31);
		tBusCounterLbl.setText(Integer.toString(randTransportValue));
		generalInfo.add(tBusCounterLbl);
		tBusCounterLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		tramLbl = new JLabel("\u041A\u0456\u043B\u044C\u043A\u0456\u0441\u0442\u044C \u0430\u043A\u0442\u0438\u0432\u043D\u0438\u0445 \u0442\u0440\u0430\u043C\u0432\u0430\u0457\u0432");
		tramLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tramLbl.setBounds(913, 63, 200, 31);
		generalInfo.add(tramLbl);
		tramLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		tramCounterLbl = new JLabel("Trolleybus counter");
		tramCounterLbl.setFont(new Font("Tahoma", Font.PLAIN, 13));
		tramCounterLbl.setBounds(913, 102, 200, 31);
		tramCounterLbl.setText((Integer.toString(randTransportValue)));
		generalInfo.add(tramCounterLbl);
		tramCounterLbl.setHorizontalAlignment(SwingConstants.CENTER);
		
		actionBtns = new JPanel();
		actionBtns.setBorder(new LineBorder(new Color(0, 0, 0)));
		actionBtns.setBounds(803, 157, 237, 206);
		mainPage.add(actionBtns);
		actionBtns.setLayout(null);
		
		detailsBtn = new JButton("Подивитися Деталі");
		detailsBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		detailsBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(activeRoutesTable.getSelectedRow()!=-1) {				
					String routeNumber = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(), 0);
					String routeTransportType = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(), 1);
					Route route = routeDAO.getRoutebyIDandType(routeNumber, routeTransportType);
					route.setAmountOfActiveTransport((int) Math.floor(Math.random()*(6-2+1)+2));
					activeTransportCount.setText(Integer.toString(route.getAmountOfActiveTransport()));
					reserveTransportCount.setText(Integer.toString(15-route.getAmountOfActiveTransport()));
					List<Integer> routeStops = route.getRoute();
					List<RouteStop> stops = new ArrayList<RouteStop>();
					for (int i=0;i<routeStops.size();i++) {
						RouteStop buffStop = routeStopDAO.getStopByID(routeStops.get(i));
						stops.add(buffStop);
					}
					updateSingleRouteTable(route);
					updateRouteStopsTable(stops);
					CardLayout cl = (CardLayout)(frame.getContentPane().getLayout());                 
					cl.show(frame.getContentPane(), "routeDetailsPanel");
				}
				else JOptionPane.showMessageDialog(frame, "Оберіть маршрут");
			}
		});
		detailsBtn.setBounds(6, 16, 225, 82);
		actionBtns.add(detailsBtn);
		
		mapViewBtn = new JButton("Відкрити на мапі");
		mapViewBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(activeRoutesTable.getSelectedRow()!=-1) {
					String routeId = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(),0);
					String routeType = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(),1);
					Route route = routeDAO.getRoutebyIDandType(routeId, routeType);
					//System.out.println(route.getFirstStop()+route.getLastStop());
					String s = route.toRouteQuerry();
					s = s.replace('"', "'".charAt(0));
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(URI.create(s));
					} catch (IOException e1) {
						//System.out.println(s);
						e1.printStackTrace();
					}
				}
				else JOptionPane.showMessageDialog(frame,
					    "Оберіть маршрут для відображення на мапі.",
					    "Map Error",
					    JOptionPane.ERROR_MESSAGE);
			}
		});
		mapViewBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		mapViewBtn.setBounds(6, 110, 225, 82);
		actionBtns.add(mapViewBtn);
		
		srchPanel = new JPanel();
		srchPanel.setBorder(new LineBorder(new Color(0, 0, 0)));
		srchPanel.setBounds(1050, 157, 301, 206);
		mainPage.add(srchPanel);
		srchPanel.setLayout(null);
		
		srchLabel = new JLabel("Пошук Маршруту");
		srchLabel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		srchLabel.setBounds(6, 11, 285, 36);
		srchPanel.add(srchLabel);
		srchLabel.setHorizontalAlignment(SwingConstants.CENTER);
		
		criterialbl = new JLabel("Пошук за:");
		criterialbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		criterialbl.setBounds(6, 58, 131, 22);
		srchPanel.add(criterialbl);
		criterialbl.setLabelFor(criteriaBox);
		
		criteriaBox = new JComboBox<String>();
		criteriaBox.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent event) {
				rtSrchField.removeAllItems();
				switch ((String)event.getItem()) {
				case ("Номер маршруту"):
					List<String> routeNumbers = routeDAO.getUniqueRouteIDs();
					for (int i=0;i<routeNumbers.size();i++) {
						rtSrchField.addItem(routeNumbers.get(i));
					}	
					break;
				case ("Тип маршруту"):
					rtSrchField.setModel(new DefaultComboBoxModel(new String[] {"Автобус","Тролейбус","Трамвай"}));;
					break;
				case ("Номер транспорту"):					
					List<Integer> transportIDs = transportDAO.getAllTranportIDs();
					for (int i=0;i<transportIDs.size();i++) {
						rtSrchField.addItem(transportIDs.get(i).toString());
					}				
					break;
				case ("Назва зупинки"):
					List<RouteStop> routeStops = routeStopDAO.getAllStops();
					for (int i=0;i<routeStops.size();i++) {
						rtSrchField.addItem(routeStops.get(i).toString());
					}
					break;
				}
			}
		});
		criteriaBox.setBounds(6, 93, 199, 22);
		srchPanel.add(criteriaBox);
		String values[] = {"Номер маршруту", "Тип маршруту", "Номер транспорту", "Назва зупинки"};
		criteriaBox.setModel(new DefaultComboBoxModel(values));
		
		rtSrchField = new JComboBox<String>();
		rtSrchField.setBounds(6, 125, 199, 24);
		List<String> routeNumbers = routeDAO.getUniqueRouteIDs();
		for (int i=0;i<routeNumbers.size();i++) {
			rtSrchField.addItem(routeNumbers.get(i));
		};
		srchPanel.add(rtSrchField);
		
		srchBtn = new JButton("Пошук");
		srchBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String criteria = (String)criteriaBox.getSelectedItem();
				List<Route> routes = new ArrayList<Route>();
				switch(criteria) {
				case ("Номер маршруту"):
					routes = routeDAO.getRoutesByID((String)rtSrchField.getSelectedItem());
					updateRoutesTable(routes);
					break;
				case ("Тип маршруту"):
					routes = routeDAO.getRoutesByType((String)rtSrchField.getSelectedItem());
					updateRoutesTable(routes);
					break;
				case ("Номер транспорту"):
					Transport transport = transportDAO.getTransportByID(Integer.parseInt((String) rtSrchField.getSelectedItem()));
					routes.add(routeDAO.getRoutebyIDandType(transport.getAssignedRoute(), transport.getTransportType()));
					updateRoutesTable(routes);
					break;
				case ("Назва зупинки"):
					String buffStop = (String)rtSrchField.getSelectedItem();
					String routeStop[] = buffStop.split(" ");
					//System.out.println(routeStop[0]);
					int stopID = Integer.parseInt(routeStop[0]);
					List<Route> routesForcheck = routeDAO.getAllRoutes();
					List<Route> routesForTable = new ArrayList<Route>();
					for(int i=0;i<routesForcheck.size();i++) {
						for (int element :routesForcheck.get(i).getRoute()) {
							if(element == stopID) {
								Route route = routesForcheck.get(i);
								routesForTable.add(route);
							}
						}
					}
					updateRoutesTable(routesForTable);
					break;
				default:
					JOptionPane.showMessageDialog(frame, "Не вибрано усі критерії пошуку");
					break;
				}
			}
		});
		srchBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		srchBtn.setBounds(6, 157, 80, 32);
		srchPanel.add(srchBtn);
		
		JButton refreshBtn = new JButton("Відміна");
		refreshBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				refreshRoutesTable();
			}
		});
		refreshBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		refreshBtn.setBounds(125, 157, 80, 32);
		srchPanel.add(refreshBtn);
		
		adminBtns = new JPanel();
		adminBtns.setBorder(new LineBorder(new Color(0, 0, 0)));
		adminBtns.setBounds(803, 372, 237, 206);
		mainPage.add(adminBtns);
		adminBtns.setLayout(null);
		
		routeEditBtn = new JButton("Відредагувати маршрут");
		routeEditBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		routeEditBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(activeRoutesTable.getSelectedRow()!=-1) {
					String s = (String)JOptionPane.showInputDialog(
							frame,
		                    "Введіть код доступу",
		                    "Авторизація",
		                    JOptionPane.PLAIN_MESSAGE);
					try {
						if(s.equals("adminPass")) {
							CardLayout cl = (CardLayout)(frame.getContentPane().getLayout());                 
							cl.show(frame.getContentPane(), "routeEdit");
							String routeNumber = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(), 0);
							String routeTransportType = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(), 1);
							Route route = routeDAO.getRoutebyIDandType(routeNumber, routeTransportType);
							List<Integer> routeStops = route.getRoute();
							List<RouteStop> stops = new ArrayList<RouteStop>();
							for (int i=0;i<routeStops.size();i++) {
								RouteStop buffStop = routeStopDAO.getStopByID(routeStops.get(i));
								stops.add(buffStop);
							}
							updateSingleRouteTable(route);
							updateRouteStopsTable(stops);
						}
						else JOptionPane.showMessageDialog(frame,
							    "Incorrect access code.",
							    "Authorization error",
							    JOptionPane.ERROR_MESSAGE);
					} catch (HeadlessException e1) {
						JOptionPane.showMessageDialog(frame,
							    "Nothing was selected.",
							    "Selection error",
							    JOptionPane.ERROR_MESSAGE);
						e1.printStackTrace();
					}
				} else JOptionPane.showMessageDialog(frame, "Оберіть маршрут");
			}
		});
		routeEditBtn.setBounds(6, 16, 225, 82);
		adminBtns.add(routeEditBtn);
		
		changeRouteStatusBtn = new JButton("Змінити статус маршруту");
		changeRouteStatusBtn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(activeRoutesTable.getSelectedRow()!=-1) {
					String s = (String)JOptionPane.showInputDialog(
							frame,
		                   "Введіть код доступу",
		                   "Авторизація",
		                   JOptionPane.PLAIN_MESSAGE);
					if(s.equals("adminPass")) {						
						String routeNumber = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(), 0);
						String routeTransportType = (String) activeRoutesTable.getValueAt(activeRoutesTable.getSelectedRow(), 1);
						Route route = routeDAO.getRoutebyIDandType(routeNumber, routeTransportType);
						if (route.getRouteStatus().contentEquals("Активний")) route.setRouteStatus("Неактивний");
						else route.setRouteStatus("Активний");
						routeDAO.updateRoute(route);
						refreshRoutesTable();
					}
					else JOptionPane.showMessageDialog(frame,
							   "Incorrect access code.",
							   "Authorization error",
							   JOptionPane.ERROR_MESSAGE);
				} else JOptionPane.showMessageDialog(frame, "Оберіть маршрут");
			}
		});
		changeRouteStatusBtn.setFont(new Font("Tahoma", Font.PLAIN, 14));
		changeRouteStatusBtn.setBounds(6, 110, 225, 82);
		adminBtns.add(changeRouteStatusBtn);
		
		scrollPane = new JScrollPane();
		scrollPane.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPane.setBounds(10, 157, 789, 703);
		mainPage.add(scrollPane);
		
		activeRoutesTable = new JTable(tableModelRoutes);
		activeRoutesTable.setFocusable(false);
		activeRoutesTable.setFillsViewportHeight(true);
		activeRoutesTable.setRowHeight(40);
		scrollPane.setViewportView(activeRoutesTable);
		activeRoutesTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		List<Route> routes = routeDAO.getAllRoutes();
		updateRoutesTable(routes);
		
		
		routeEditPanel = new JPanel();
		frame.getContentPane().add(routeEditPanel, "routeEdit");
		routeEditPanel.setLayout(null);
		
		titlePanel = new JPanel();
		titlePanel.setBounds(0, 0, 1434, 175);
		routeEditPanel.add(titlePanel);
		titlePanel.setLayout(null);
		
		lblNewLabel = new JLabel("\u0420\u0435\u0434\u0430\u0433\u0443\u0432\u0430\u043D\u043D\u044F \u043C\u0430\u0440\u0448\u0440\u0443\u0442\u0443");
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 56));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 50, 1414, 86);
		titlePanel.add(lblNewLabel);
		
		scrollPane_4 = new JScrollPane();
		scrollPane_4.setBounds(10, 186, 863, 69);
		routeEditPanel.add(scrollPane_4);
		
		routeInfo = new JTable(tableModelSingleRoute);
		routeInfo.setRowHeight(45);
		scrollPane_4.setViewportView(routeInfo);
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPane_1.setBounds(10, 266, 512, 351);
		routeEditPanel.add(scrollPane_1);
		
		stopsInfo = new JTable(tableModelRouteStops);
		scrollPane_1.setViewportView(stopsInfo);
		
		btnAddStop = new JButton("Додати зупинку");
		btnAddStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				ImageIcon icon = new ImageIcon();
				Route route = new Route();
				List<RouteStop> stops = routeStopDAO.getAllStops();
				Object[] stopsPossibilities = new Object[stops.size()];
				for (int i=0;i<stops.size();i++) {
					stopsPossibilities[i] =(Object) stops.get(i).toString();
				}
				String s = (String) JOptionPane.showInputDialog(frame, "Оберіть зупинку для додавання", "Редагування маршруту",
						JOptionPane.PLAIN_MESSAGE, icon, stopsPossibilities, stopsPossibilities[0]);
				if(s!=null) {
					String routeStop[] = s.split(" ");
					int stopID = Integer.parseInt(routeStop[0]);
					route = routeDAO.getRoutebyIDandType((String)routeInfo.getValueAt(0, 0), (String)routeInfo.getValueAt(0, 1));
					route.extendRoute(stopID);
					routeDAO.updateRoute(route);
					route.setAmountOfStops(route.getRoute().size());
				} else JOptionPane.showMessageDialog(frame, "Операція була відмінена");
				List<Integer> routeStops = route.getRoute();
				stops.clear();
				for (int i=0;i<routeStops.size();i++) {
					RouteStop buffStop = routeStopDAO.getStopByID(routeStops.get(i));
					stops.add(buffStop);
				}
				updateSingleRouteTable(route);
				updateRouteStopsTable(stops);
			}
		});
		btnAddStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnAddStop.setBounds(532, 266, 341, 60);
		routeEditPanel.add(btnAddStop);
		
		btnCancel = new JButton("\u0412\u0456\u0434\u043C\u0456\u043D\u0430");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(frame.getContentPane().getLayout());                 
				cl.show(frame.getContentPane(), "mainPage");
			}
		});
		
		btnStopChange = new JButton("\u0417\u043C\u0456\u043D\u0438\u0442\u0438 \u0437\u0443\u043F\u0438\u043D\u043A\u0443");
		btnStopChange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(stopsInfo.getSelectedRow()==0 || stopsInfo.getSelectedRow()==stopsInfo.getRowCount()-1) {
					JOptionPane.showMessageDialog(frame,
						    "Неможливо змінити початкову або кінцеву зупинку.",
						    "Editing error",
						    JOptionPane.ERROR_MESSAGE);
				} 
				else if(stopsInfo.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(frame,
					    "Оберіть зупинку.",
					    "Editing error",
					    JOptionPane.ERROR_MESSAGE);
				} else {
					ImageIcon icon = new ImageIcon();
					Route route = new Route();
					List<RouteStop> stops = routeStopDAO.getAllStops();
					Object[] stopsPossibilities = new Object[stops.size()];
					for (int i=0;i<stops.size();i++) {
						stopsPossibilities[i] =(Object) stops.get(i).toString();
					}
					String s = (String) JOptionPane.showInputDialog(frame, "Оберіть зупинку для заміни", "Редагування маршруту",
							JOptionPane.PLAIN_MESSAGE, icon, stopsPossibilities, stopsPossibilities[0]);
					if(s!=null) {
						String routeStop[] = s.split(" ");
						int stopID = Integer.parseInt(routeStop[0]);
						route = routeDAO.getRoutebyIDandType((String)routeInfo.getValueAt(0, 0), (String)routeInfo.getValueAt(0, 1));
						route.getRoute().set(stopsInfo.getSelectedRow(), stopID);
						routeDAO.updateRoute(route);
						route.setAmountOfStops(route.getRoute().size());
				} else JOptionPane.showMessageDialog(frame, "Операція була відмінена");
				List<Integer> routeStops = route.getRoute();
				stops.clear();
				for (int i=0;i<routeStops.size();i++) {
					RouteStop buffStop = routeStopDAO.getStopByID(routeStops.get(i));
					stops.add(buffStop);
				}
				updateSingleRouteTable(route);
				updateRouteStopsTable(stops);
				}
			}
		});
		btnStopChange.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStopChange.setBounds(532, 337, 341, 60);
		routeEditPanel.add(btnStopChange);
		
		JButton btnRemoveStop = new JButton("Прибрати зупинку");
		btnRemoveStop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(stopsInfo.getSelectedRow()==0 || stopsInfo.getSelectedRow()==stopsInfo.getRowCount()-1) {
					JOptionPane.showMessageDialog(frame,
							   "Cannot Remove First or Last Stop.",
							   "Editing error",
							   JOptionPane.ERROR_MESSAGE);					
				}
				else if(stopsInfo.getSelectedRow()==-1) {
					JOptionPane.showMessageDialog(frame,
					    "Оберіть зупинку.",
					    "Editing error",
					    JOptionPane.ERROR_MESSAGE);
				} else {
					Route route = routeDAO.getRoutebyIDandType((String)routeInfo.getValueAt(0, 0), (String)routeInfo.getValueAt(0, 1));
					int stopID = (int) stopsInfo.getSelectedRow();
					route.removeRouteStop(stopID);
					routeDAO.updateRoute(route);
					route.setAmountOfStops(route.getRoute().size());
					List<Integer> routeStops = route.getRoute();
					List<RouteStop> stops = new ArrayList<RouteStop>();
					for (int i=0;i<routeStops.size();i++) {
						RouteStop buffStop = routeStopDAO.getStopByID(routeStops.get(i));
						stops.add(buffStop);
					}
					updateSingleRouteTable(route);
					updateRouteStopsTable(stops);
				}
			} 
		});
		btnRemoveStop.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRemoveStop.setBounds(532, 408, 341, 60);
		routeEditPanel.add(btnRemoveStop);
		btnCancel.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnCancel.setBounds(532, 477, 341, 60);
		routeEditPanel.add(btnCancel);
		
		routeDetailsPanel = new JPanel();
		frame.getContentPane().add(routeDetailsPanel, "routeDetailsPanel");
		routeDetailsPanel.setLayout(null);
		
		detailsPanel = new JPanel();
		detailsPanel.setLayout(null);
		detailsPanel.setBounds(10, 11, 1434, 175);
		routeDetailsPanel.add(detailsPanel);
		
		lblNewLabel_1 = new JLabel("Деталі Маршруту");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 52));
		lblNewLabel_1.setBounds(10, 11, 1414, 73);
		detailsPanel.add(lblNewLabel_1);
		
		activeTransportLbl = new JLabel("Активний транспорт на маршруті");
		activeTransportLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		activeTransportLbl.setHorizontalAlignment(SwingConstants.CENTER);
		activeTransportLbl.setBounds(10, 95, 221, 29);
		detailsPanel.add(activeTransportLbl);
		
		activeTransportCount = new JLabel("counter");
		activeTransportCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		activeTransportCount.setHorizontalAlignment(SwingConstants.CENTER);
		activeTransportCount.setBounds(10, 135, 221, 29);
		detailsPanel.add(activeTransportCount);
		
		reserveTransportLbl = new JLabel("Доступний транспорт у резерві");
		reserveTransportLbl.setFont(new Font("Tahoma", Font.PLAIN, 14));
		reserveTransportLbl.setHorizontalAlignment(SwingConstants.CENTER);
		reserveTransportLbl.setBounds(241, 95, 221, 29);
		detailsPanel.add(reserveTransportLbl);
		
		reserveTransportCount = new JLabel("counter");
		reserveTransportCount.setFont(new Font("Tahoma", Font.PLAIN, 14));
		reserveTransportCount.setHorizontalAlignment(SwingConstants.CENTER);
		reserveTransportCount.setBounds(241, 135, 221, 29);
		detailsPanel.add(reserveTransportCount);
		
		scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(10, 197, 863, 69);
		routeDetailsPanel.add(scrollPane_3);
		
		routeDetInfo = new JTable(tableModelSingleRoute);
		routeDetInfo.setRowHeight(45);
		routeDetInfo.setPreferredSize(new Dimension(0, 50));
		scrollPane_3.setViewportView(routeDetInfo);
		
		scrollPane_2 = new JScrollPane();
		scrollPane_2.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
		scrollPane_2.setBounds(10, 277, 512, 351);
		routeDetailsPanel.add(scrollPane_2);
		
		routeStopsInfo = new JTable(tableModelRouteStops);
		scrollPane_2.setViewportView(routeStopsInfo);
		
		btnStopMapView = new JButton("Відкрити на карті");//TODO
		btnStopMapView.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(routeStopsInfo.getSelectedRow()!=-1) {
					RouteStop routeStop = routeStopDAO.getStopByAddress((String)routeStopsInfo.getValueAt(routeStopsInfo.getSelectedRow(), 1));
					String s = routeStop.toSearchQuerry();
					s = s.replace('"', "'".charAt(0));
					Desktop desktop = Desktop.getDesktop();
					try {
						desktop.browse(URI.create(s));
					} catch (IOException e1) {
						e1.printStackTrace();
					}
				} else JOptionPane.showMessageDialog(frame,
					    "Оберіть зупинку для відображення на мапі.",
					    "Map Error",
					    JOptionPane.ERROR_MESSAGE); 
			}
		});
		btnStopMapView.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnStopMapView.setBounds(532, 277, 341, 60);
		routeDetailsPanel.add(btnStopMapView);
		
		btnSendVehicle = new JButton("Вислати транспорт");
		btnSendVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(reserveTransportCount.getText())>0) {
					int actCounter = Integer.parseInt(activeTransportCount.getText()) + 1;
					int resCounter = Integer.parseInt(reserveTransportCount.getText()) - 1;
					activeTransportCount.setText(Integer.toString(actCounter));
					reserveTransportCount.setText(Integer.toString(resCounter));
					if(routeDetInfo.getValueAt(0, 1).equals("Автобус"))
						busCounterLbl.setText(Integer.toString(actCounter));
					else if(routeDetInfo.getValueAt(0, 1).equals("Тролейбус")) 
						tBusCounterLbl.setText(Integer.toString(actCounter));
					else tramCounterLbl.setText(Integer.toString(actCounter));
					JOptionPane.showMessageDialog(frame, "Один "+ (String)routeDetInfo.getValueAt(0, 1)+ " був висланий на маршрут");
				} else JOptionPane.showMessageDialog(frame, "Більше немає доступного транспорту у резерві!", "Update Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnSendVehicle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnSendVehicle.setBounds(532, 348, 341, 60);
		routeDetailsPanel.add(btnSendVehicle);
		
		btnRecallVehicle = new JButton("Відкликати Транспорт");
		btnRecallVehicle.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if(Integer.parseInt(activeTransportCount.getText())>0) {
					int actCounter = Integer.parseInt(activeTransportCount.getText()) - 1;
					int resCounter = Integer.parseInt(reserveTransportCount.getText()) + 1;
					activeTransportCount.setText(Integer.toString(actCounter));
					reserveTransportCount.setText(Integer.toString(resCounter));
					if(routeDetInfo.getValueAt(0, 1).equals("Автобус"))
						busCounterLbl.setText(Integer.toString(actCounter));
					else if(routeDetInfo.getValueAt(0, 1).equals("Тролейбус")) 
						tBusCounterLbl.setText(Integer.toString(actCounter));
					else tramCounterLbl.setText(Integer.toString(actCounter));
					JOptionPane.showMessageDialog(frame, "Один "+ (String)routeDetInfo.getValueAt(0, 1)+ " був відкликаний з маршруту");
				} else JOptionPane.showMessageDialog(frame, "На маршруті не залишилося активного транспорту!", "Update Error", JOptionPane.ERROR_MESSAGE);
			}
		});
		btnRecallVehicle.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnRecallVehicle.setBounds(532, 418, 341, 60);
		routeDetailsPanel.add(btnRecallVehicle);
		
		btnGoBack = new JButton("Повернутися");
		btnGoBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				CardLayout cl = (CardLayout)(frame.getContentPane().getLayout());                 
				cl.show(frame.getContentPane(), "mainPage");
			}
		});
		btnGoBack.setFont(new Font("Tahoma", Font.PLAIN, 14));
		btnGoBack.setBounds(532, 489, 341, 60);
		routeDetailsPanel.add(btnGoBack);
	}
	
	public void updateRoutesTable(List<Route> routes){
		tableModelRoutes.setRowCount(0);
        if(!routes.isEmpty()) {
            String columnsHeader[] = {"Номер маршруту", "Тип транспорту", "Кількість зупинок", "Маршрут", "Статус маршруту", "Початок маршруту",
                    "Кінцева зупинка"};
            tableModelRoutes.setColumnIdentifiers(columnsHeader);
            for(int i = 0; i < routes.size(); i++) {
            	tableModelRoutes.insertRow(i, new Object[] {routes.get(i).getRouteNumber(),routes.get(i).getTransportType(),
                    routes.get(i).getAmountOfStops(),routes.get(i).getRoute(),routes.get(i).getRouteStatus(),routes.get(i).getFirstStop(),routes.get(i).getLastStop()});
            }
        }
	}
	
	public void updateSingleRouteTable(Route route){
		tableModelSingleRoute.setRowCount(0);
        if(!route.equals(null)) {
            String columnsHeader[] = {"Номер маршруту", "Тип транспорту", "Кількість зупинок", "Маршрут", "Статус маршруту", "Початок маршруту",
                    "Кінцева зупинка"};
            tableModelSingleRoute.setColumnIdentifiers(columnsHeader);
            tableModelSingleRoute.insertRow(0, new Object[] {route.getRouteNumber(),route.getTransportType(), route.getAmountOfStops(),
            			route.getRoute(),route.getRouteStatus(),route.getFirstStop(),route.getLastStop()});
            }
        }
	
	public void updateRouteStopsTable(List<RouteStop> routeStops){
		tableModelRouteStops.setRowCount(0);
        if(!routeStops.isEmpty()) {
            String columnsHeader[] = {"ID", "Адресса"};
            tableModelRouteStops.setColumnIdentifiers(columnsHeader);
            for(int i = 0; i < routeStops.size(); i++) {
            	tableModelRouteStops.insertRow(i, new Object[] {routeStops.get(i).getId(),routeStops.get(i).getAddress()});
            }
        }
	}
	
	public void refreshRoutesTable(){
		List<Route> routes = routeDAO.getAllRoutes();
		tableModelRoutes.setRowCount(0);
        if(!routes.isEmpty()) {
            String columnsHeader[] = {"Номер маршруту", "Тип транспорту", "Кількість зупинок", "Маршрут", "Статус маршруту", "Початок маршруту",
                    "Кінцева зупинка"};
            tableModelRoutes.setColumnIdentifiers(columnsHeader);
            for(int i = 0; i < routes.size(); i++) {
            	tableModelRoutes.insertRow(i, new Object[] {routes.get(i).getRouteNumber(),routes.get(i).getTransportType(),
                    routes.get(i).getAmountOfStops(),routes.get(i).getRoute(),routes.get(i).getRouteStatus(),routes.get(i).getFirstStop(),routes.get(i).getLastStop()});
            }
        }
	}
}
