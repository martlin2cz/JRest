package cz.martlin.jrest.test.pi.swingCli;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import cz.martlin.jrest.guest.JRestGuest;
import cz.martlin.jrest.misc.JRestException;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleRequest;
import cz.martlin.jrest.protocol.reqresps.simple.SimpleResponse;
import cz.martlin.jrest.test.pi.calcApp.PiComputerHandler;

/**
 * The main frame of the swing client app.
 * 
 * @author martin
 *
 */
public class JPiClientFrame extends JFrame {

	private static final long serialVersionUID = -1311504008246241895L;

	private final JRestGuest<SimpleRequest, SimpleResponse> guest;

	private JLabel headerLbl;

	private DefaultListModel<String> model;
	private JList<String> list;

	private JPanel buttsPane;
	private JScrollPane scroll;
	private JButton toggleButt;
	private JButton statusButt;
	private JButton getPiButt;
	private JButton getMorePrecPiButt;
	private JButton getNButt;
	private JButton isRunningButt;

	public JPiClientFrame(JRestGuest<SimpleRequest, SimpleResponse> guest) {
		super("JPiClientApp");

		this.guest = guest;

		initializeComponents();

		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setPreferredSize(new Dimension(800, 300));
		pack();
	}

	/**
	 * Initializes components of the frame.
	 */
	private void initializeComponents() {
		headerLbl = new JLabel("<html><RQT, RST>Click buttons down the frame to invoke some requests. "
				+ "In list bellow will be listed data fields of responses. "
				+ "Complete responses will be printed to stdout.</p></html>");
		getContentPane().add(headerLbl, BorderLayout.NORTH);

		model = new DefaultListModel<>();
		list = new JList<>(model);
		scroll = new JScrollPane(list);
		getContentPane().add(scroll, BorderLayout.CENTER);

		buttsPane = initializeButtonsPanel();
		getContentPane().add(buttsPane, BorderLayout.SOUTH);

	}

	/**
	 * Initializes the panel with the buttons.
	 * 
	 * @return
	 */
	private JPanel initializeButtonsPanel() {
		JPanel pane = new JPanel();

		String toggleButtLabel = askRunningAndGetLabel();
		toggleButt = new JButton(toggleButtLabel);
		toggleButt.addActionListener(new ToogleButtActionListener());
		pane.add(toggleButt);

		isRunningButt = new JButton("Is running?");
		isRunningButt.addActionListener(new IsRunningActionListener());
		pane.add(isRunningButt);

		statusButt = new JButton("Status");
		statusButt.addActionListener(new StatusButtActionListener());
		pane.add(statusButt);

		getPiButt = new JButton("Get pi");
		getPiButt.addActionListener(new GetPiButtActionListener());
		pane.add(getPiButt);

		getMorePrecPiButt = new JButton("Get pi (more prec)");
		getMorePrecPiButt.addActionListener(new GetMorePrecPiButtActionListener());
		pane.add(getMorePrecPiButt);

		getNButt = new JButton("Get n");
		getNButt.addActionListener(new GetNButtActionListener());
		pane.add(getNButt);

		return pane;
	}

	/**
	 * Sends request to test if is computing running or not. And then returns
	 * "Stop" or "Start" corresponding to what should the {@link #toggleButt} on
	 * click do.
	 * 
	 * @return
	 */
	private String askRunningAndGetLabel() {
		SimpleRequest req = new SimpleRequest(PiComputerHandler.IS_RUNNING_COMMAND);
		SimpleResponse resp;
		try {
			resp = guest.sendRequest(req);
		} catch (JRestException e) {
			error(e.toString());
			return "!!!";
		}

		if ("yes".equals(resp.getData())) {
			return "Stop";
		} else if ("no".equals(resp.getData())) {
			return "Start";
		} else {
			error("Uknown state");
			return "???";
		}
	}

	/**
	 * Sends given request and handles the response (if some, if not failed).
	 * 
	 * @param request
	 */
	protected void sendAndHandle(SimpleRequest request) {
		try {
			SimpleResponse response = guest.sendRequest(request);

			handleResponse(response, request);

		} catch (JRestException e) {
			e.printStackTrace();
			error(e.toString());
		}
	}

	/**
	 * Handles the given response of given request. Data adds to list, whole
	 * response prints to stdout and if warning or worse occured displays
	 * messagebox.
	 * 
	 * @param resp
	 * @param req
	 */
	private void handleResponse(SimpleResponse resp, SimpleRequest req) {
		System.out.println("Response: " + resp);
		model.addElement(resp.getData());

		switch (resp.getStatus()) {
		case ERROR:
		case FATAL:
			error(resp.toString());
			break;
		case WARN:
			warning(resp.toString());
			break;
		default:
			break;
		}

		setToggleButtText(resp, req);
	}

	/**
	 * Sets the text of the {@link #toggleButt} by the given response (of given
	 * request).
	 * 
	 * @param resp
	 * @param req
	 */
	private void setToggleButtText(SimpleResponse resp, SimpleRequest req) {

		if (req.getCommand().equals(PiComputerHandler.COMPLETE_INFO_COMMAND)) {
			return;
		}

		String status;
		if (req.getCommand().equals(PiComputerHandler.START_COMMAND)
				|| req.getCommand().equals(PiComputerHandler.STOP_COMMAND)) {
			status = resp.getData();
		} else if (req.getCommand().equals(PiComputerHandler.IS_RUNNING_COMMAND)) {
			if (resp.getData().equals("yes")) {
				status = "running";
			} else if (resp.getData().equals("no")) {
				status = "stopped";
			} else {
				status = "unkwnown";
			}
		} else {
			status = resp.getMeta();
		}

		if ("running".equals(status)) {
			toggleButt.setText("Stop");

		} else if ("stopped".equals(status)) {
			toggleButt.setText("Start");

		} else {
			warning("Unknown state");
		}
	}

	/**
	 * Shows messagebox with warning.
	 * 
	 * @param message
	 */
	private void warning(String message) {
		JOptionPane.showConfirmDialog(this, message, "Warning", JOptionPane.DEFAULT_OPTION,
				JOptionPane.WARNING_MESSAGE);
	}

	/**
	 * Shows messagebox with error.
	 * 
	 * @param message
	 */
	private void error(String message) {
		JOptionPane.showConfirmDialog(this, message, "Error", JOptionPane.DEFAULT_OPTION, JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Action listener for "get current n" button.
	 * 
	 * @author martin
	 *
	 */
	public class GetNButtActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SimpleRequest request = new SimpleRequest("gimme-current-n");
			sendAndHandle(request);
		}

	}

	/**
	 * Action listener for "get current pi with more precission" button.
	 * 
	 * @author martin
	 *
	 */
	public class GetMorePrecPiButtActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SimpleRequest request = new SimpleRequest("gimme-current-pi", Integer.toString(20));
			sendAndHandle(request);
		}

	}

	/**
	 * Action listener for "get current pi" button.
	 * 
	 * @author martin
	 *
	 */
	public class GetPiButtActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SimpleRequest request = new SimpleRequest("gimme-current-pi");
			sendAndHandle(request);
		}

	}

	/**
	 * Action listener for "is running?" button.
	 * 
	 * @author martin
	 *
	 */
	public class IsRunningActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SimpleRequest request = new SimpleRequest(PiComputerHandler.IS_RUNNING_COMMAND);
			sendAndHandle(request);
		}

	}

	/**
	 * Action listener for "get complete status" button.
	 * 
	 * @author martin
	 *
	 */
	public class StatusButtActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			SimpleRequest request = new SimpleRequest(PiComputerHandler.COMPLETE_INFO_COMMAND);
			sendAndHandle(request);
		}

	}

	/**
	 * Action listener for "toggle start/stop" button.
	 * 
	 * @author martin
	 *
	 */
	public class ToogleButtActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton toggleButt = (JButton) e.getSource();

			SimpleRequest request;

			if (toggleButt.getText().equals("Start")) {
				request = new SimpleRequest(PiComputerHandler.START_COMMAND);

			} else if (toggleButt.getText().equals("Stop")) {
				request = new SimpleRequest(PiComputerHandler.STOP_COMMAND);

			} else {
				throw new IllegalStateException("Unknown state");
			}

			sendAndHandle(request);
		}

	}
}
