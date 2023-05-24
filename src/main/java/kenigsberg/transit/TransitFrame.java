package kenigsberg.transit;

import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TransitFrame extends JFrame {
    private Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("https://bustime.mta.info/")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .build();


    private TransitService service = retrofit.create(TransitService.class);

    private JLabel limitedInfoLabel;
    private TransitController controller;
    public TransitFrame() {

        setSize(800, 500);
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

        topPanel.add(stopPanel, BorderLayout.CENTER);

        JPanel detailStopPanel = new JPanel();
        detailStopPanel.setLayout(new BoxLayout(detailStopPanel, BoxLayout.Y_AXIS));

        limitedInfoLabel = new JLabel();
        limitedInfoLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        limitedInfoLabel.setForeground(Color.LIGHT_GRAY);
        detailStopPanel.add(limitedInfoLabel);


        controller = new TransitController(service, limitedInfoLabel, detailStopPanel);

        mainPanel.add(detailStopPanel, BorderLayout.CENTER);
        mainPanel.add(topPanel,BorderLayout.NORTH);
        setContentPane(mainPanel);

        provideStopInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.determineProvideInfo(refNumber.getText());
            }
        });
    }
}
