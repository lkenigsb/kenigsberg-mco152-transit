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
    private JLabel completeStopInfo;
    private TransitController controller;
    public TransitFrame() {

        setSize(800, 300);
        setTitle("NYC Transit");
        //What happens when we hit the exit button
        setDefaultCloseOperation(EXIT_ON_CLOSE);


        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());


        JLabel title = new JLabel("NYC BUS TRANSIT", SwingConstants.CENTER);
        title.setFont(new Font(Font.SANS_SERIF, Font.BOLD, 20));
        title.setForeground(Color.BLACK);
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel stopPanel = new JPanel();
        stopPanel.setLayout(new BorderLayout());

        JTextField refNumber = new JTextField("Stop Reference Number");
        stopPanel.add(refNumber, BorderLayout.CENTER);

        JButton provideStopInfo = new JButton("Provide Stop Info");
        stopPanel.add(provideStopInfo, BorderLayout.EAST);

        JPanel detailStopPanel = new JPanel();
        detailStopPanel.setLayout(new FlowLayout());

        limitedInfoLabel = new JLabel();
        limitedInfoLabel.setFont(new Font(Font.MONOSPACED, Font.BOLD, 15));
        limitedInfoLabel.setForeground(Color.LIGHT_GRAY);
        detailStopPanel.add(limitedInfoLabel);
        limitedInfoLabel.setHorizontalAlignment(SwingConstants.CENTER);


        completeStopInfo = new JLabel();
        detailStopPanel.add(completeStopInfo);

        controller = new TransitController(service, limitedInfoLabel, completeStopInfo);

        stopPanel.add(detailStopPanel, BorderLayout.SOUTH);
        mainPanel.add(stopPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        provideStopInfo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.determineProvideInfo(refNumber.getText());
            }
        });
    }
}
