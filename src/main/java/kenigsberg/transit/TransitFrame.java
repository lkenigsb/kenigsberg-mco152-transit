package kenigsberg.transit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.swing.*;
import javax.swing.event.ListDataListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.inject.Inject;
import javax.inject.Named;

public class TransitFrame extends JFrame {

    /*
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://bustime.mta.info/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();


    private TransitService service = retrofit.create(TransitService.class);
*/
    private TransitController controller;
    private JLabel limitedInfoLabel;

    private DefaultTableModel defaultTableModel;


    @Inject
    public TransitFrame(TransitController controller,
                        @Named("limitedInfo") JLabel limitedInfoLabel,
                        @Named("defaultTableModel") DefaultTableModel defaultTableModel) {

        this.controller = controller;
        this.limitedInfoLabel = limitedInfoLabel;
        this.defaultTableModel = defaultTableModel;

        setSize(800, 600);
        setTitle("NYC Transit");
        //What happens when we hit the exit button
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JPanel topPanel = new JPanel();
        topPanel.setLayout(new BorderLayout());

        JPanel stopPanel = new JPanel();
        stopPanel.setLayout(new BorderLayout());

        JTextField refNumber = new JTextField("Please Enter Stop Reference Number");
        stopPanel.add(refNumber, BorderLayout.NORTH);

        JButton provideStopInfo = new JButton("Provide Stop Info");
        stopPanel.add(provideStopInfo, BorderLayout.EAST);
        provideStopInfo.setPreferredSize(new Dimension(250, 30));


        topPanel.add(stopPanel, BorderLayout.CENTER);

        JPanel detailStopPanel = new JPanel();
        detailStopPanel.setLayout(new BoxLayout(detailStopPanel, BoxLayout.Y_AXIS));

        limitedInfoLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        limitedInfoLabel.setForeground(Color.LIGHT_GRAY);
        detailStopPanel.add(limitedInfoLabel);

        JButton stopReferenceButton = new JButton();
        stopReferenceButton.setText("Click here for stop reference numbers");
        stopReferenceButton.setPreferredSize(new Dimension(250, 10));


        JTable table = new JTable(defaultTableModel);

        detailStopPanel.add(add(new JScrollPane(table)));
        mainPanel.add(stopReferenceButton, BorderLayout.EAST);
        mainPanel.add(detailStopPanel, BorderLayout.SOUTH);
        mainPanel.add(topPanel, BorderLayout.NORTH);
        setContentPane(mainPanel);

        provideStopInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.determineProvideInfo(refNumber.getText());
            }
        });

        stopReferenceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    java.awt.Desktop.getDesktop().browse(java.net.URI.create(
                            "https://bustime.mta.info/m/index?q=SIM33C&l=&t="));
                } catch (Exception exception) {
                    System.out.println(exception.getMessage());
                }
            }
        });
    }
}
