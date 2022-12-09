package com.example.swe206project;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.Glow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class ProfilePageController implements Initializable {

    private User user = LoginFormController.user;

    @FXML
    private Button cancel;

    @FXML
    private ImageView editPhotoIcon;

    @FXML
    private Button editProfile;

    @FXML
    private TextField height;

    @FXML
    private TextField name;

    @FXML
    private ImageView profilePhoto;

    @FXML
    private Button save;

    @FXML
    private TextField statusOrCount;

    @FXML
    private Label statusOrCountLabel;

    @FXML
    private ImageView subscriptionButton;

    @FXML
    private TextField trainerOrSpeciality;

    @FXML
    private Label trainerOrSpecialityLabel;

    @FXML
    private TextField username;

    @FXML
    private TextField weight;

    @FXML
    private ImageView wrongInputIcon;

    @FXML
    private Label wrongInputMessage;


    private boolean editProfileIsClicked = false;

    @FXML
    public void initialize() {
        name.setText(user.getName());
        height.setText(user.getHeight() + " cm");
        weight.setText(user.getWeight() + " kg");

        if (user instanceof Trainee) {
            Trainee trainee = (Trainee) user;

            if (trainee.isActive(trainee.getUsername())) {
                statusOrCount.setText("Active");
            } else {
                statusOrCount.setText("not Active");
            }
            username.setText(trainee.getUsername());
            trainerOrSpeciality.setText(trainee.trainer);
        } else {
            Trainer trainer = (Trainer) user;

            username.setText(trainer.getUsername());
            trainerOrSpecialityLabel.setText("Speciality");
            trainerOrSpeciality.setText(trainer.getSpeciality());
            statusOrCountLabel.setText("Trainees count");
            statusOrCount.setText(trainer.getTraineesList().size() + " trainees");
        }
    }

    // Edit profile button design
    @FXML
    void editProfileOnClicked(MouseEvent event) {
        editProfileIsClicked = true;

        subscriptionButton.setVisible(false);

        editProfile.setVisible(false);
        editProfile.setDisable(true);

        save.setVisible(true);
        save.setDisable(false);

        cancel.setVisible(true);
        cancel.setDisable(false);

        editPhotoIcon.setVisible(true);
        editPhotoIcon.setOpacity(0.75);
        profilePhoto.setOpacity(0.25);

        weight.setStyle(
            "-fx-border-color: #43896B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #313131; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
        );
        weight.setEditable(true);

        height.setStyle(
            "-fx-border-color: #43896B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #313131; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
        );
        height.setEditable(true);
    }

    @FXML
    void editProfileOnPressed(MouseEvent event) {
        editProfile.setStyle(
            "-fx-background-color: #366D55; -fx-background-radius: 15; -fx-cursor: hand"
        );
        editProfile.setEffect(new Glow(0.3));
    }

    @FXML
    void editProfileOnReleased(MouseEvent event) {
        editProfile.setStyle(
            "-fx-background-color: #43896B; -fx-background-radius: 15; -fx-cursor: hand"
        );
        editProfile.setEffect(new Glow(0.0));
    }

    @FXML
    void editProfileInHover(MouseEvent event) {
        editProfile.setStyle(
            "-fx-background-color: #366D55; -fx-background-radius: 15; -fx-cursor: hand"
        );
    }

    @FXML
    void editProfileOutHover(MouseEvent event) {
        editProfile.setStyle(
            "-fx-background-color: #43896B; -fx-background-radius: 15"
        );
    }

    // Save design
    @FXML
    void saveOnClicked(MouseEvent event) {
        try {
            double newWeight = Double.valueOf(weight.getText().replaceAll("\\p{Alpha}*", ""));
            double newHeight = Double.valueOf(height.getText().replaceAll("\\p{Alpha}*", ""));

            if (newWeight < 0 || newHeight < 0) {
                throw new Exception();
            }
            ReadFiles r = new ReadFiles<>("UserInfo.txt");
            int line = r.getLine(user.getUsername());
            WriteFiles w = new WriteFiles<>("UserInfo.txt");

            
            w.modifyLine(line, newWeight+"", user.weight+"");
            user.setWeight(newWeight);

            w.modifyLine(line, newHeight+"", user.height+"");
            user.setHeight(newHeight);

            editProfileIsClicked = false;

            subscriptionButton.setVisible(true);

            editProfile.setVisible(true);
            editProfile.setDisable(false);

            save.setVisible(false);
            save.setDisable(true);

            cancel.setVisible(false);
            cancel.setDisable(true);

            editPhotoIcon.setVisible(false);
            profilePhoto.setOpacity(1);

            wrongInputIcon.setVisible(false);
            wrongInputMessage.setVisible(false);

            weight.setStyle(
                "-fx-border-color: #43896B; -fx-border-width: 1px 0px 0px 0px; -fx-background-color: #212121; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
            );
            weight.setEditable(false);
            weight.setText(newWeight + " kg");

            height.setStyle(
                "-fx-border-color: #43896B; -fx-border-width: 1px 0px 0px 0px; -fx-background-color: #212121; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
            );
            height.setEditable(false);
            height.setText(newHeight + " cm");

        } catch (Exception e) {
            wrongInputIcon.setVisible(true);
            wrongInputMessage.setVisible(true);
            weight.setStyle("-fx-border-color: #D53A0B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #3A4141; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1");
            height.setStyle("-fx-border-color: #D53A0B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #3A4141; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1");
        }
    }

    @FXML
    void saveOnPressed(MouseEvent event) {
        save.setStyle(
            "-fx-background-color: #366D55; -fx-background-radius: 15; -fx-cursor: hand"
        );
        save.setEffect(new Glow(0.3));
    }

    @FXML
    void saveOnReleased(MouseEvent event) {
        save.setStyle(
            "-fx-background-color: #43896B; -fx-background-radius: 15; -fx-cursor: hand"
        );
        save.setEffect(new Glow(0.0));
    }

    @FXML
    void saveInHover(MouseEvent event) {
        save.setStyle(
            "-fx-background-color: #366D55; -fx-background-radius: 15; -fx-cursor: hand"
        );
    }

    @FXML
    void saveOutHover(MouseEvent event) {
        save.setStyle(
            "-fx-background-color: #43896B; -fx-background-radius: 15"
        );
    }

    // Cancel design
    @FXML
    void cancelOnClicked(MouseEvent event) {
        editProfileIsClicked = false;

        subscriptionButton.setVisible(true);

        editProfile.setVisible(true);
        editProfile.setDisable(false);

        save.setVisible(false);
        save.setDisable(true);

        cancel.setVisible(false);
        cancel.setDisable(true);

        editPhotoIcon.setVisible(false);
        profilePhoto.setOpacity(1);

        weight.setStyle(
            "-fx-border-color: #43896B; -fx-border-width: 1px 0px 0px 0px; -fx-background-color: #212121; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
        );
        weight.setEditable(false);
        weight.setText(user.getWeight() + " kg");

        height.setStyle(
            "-fx-border-color: #43896B; -fx-border-width: 1px 0px 0px 0px; -fx-background-color: #212121; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
        );
        height.setEditable(false);
        height.setText(user.getHeight() + " cm");
    }

    @FXML
    void cancelOnPressed(MouseEvent event) {
        cancel.setStyle(
            "-fx-background-color: #366D55; -fx-background-radius: 15; -fx-cursor: hand"
        );
        cancel.setEffect(new Glow(0.3));
    }

    @FXML
    void cancelOnReleased(MouseEvent event) {
        cancel.setStyle(
            "-fx-background-color: #43896B; -fx-background-radius: 15; -fx-cursor: hand"
        );
        cancel.setEffect(new Glow(0.0));
    }

    @FXML
    void cancelInHover(MouseEvent event) {
        cancel.setStyle(
            "-fx-background-color: #366D55; -fx-background-radius: 15; -fx-cursor: hand"
        );
    }

    @FXML
    void cancelOutHover(MouseEvent event) {
        cancel.setStyle(
            "-fx-background-color: #43896B; -fx-background-radius: 15"
        );
    }

    // Weight design
    @FXML
    void weightInHover(MouseEvent event) {
        if (editProfileIsClicked) {
            weight.setStyle(
                "-fx-cursor: text; -fx-border-color: #43896B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #3A4141; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
            );
        }
    }

    @FXML
    void weightOutHover(MouseEvent event) {
        if (editProfileIsClicked) {
            weight.setStyle(
                "-fx-border-color: #43896B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #313131; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
            );
        }
    }

    // Height design
    @FXML
    void heightInHover(MouseEvent event) {
        if (editProfileIsClicked) {
            height.setStyle(
                "-fx-cursor: text; -fx-border-color: #43896B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #3A4141; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
            );
        }
    }

    @FXML
    void heightOutHover(MouseEvent event) {
        if (editProfileIsClicked) {
            height.setStyle(
                "-fx-border-color: #43896B; -fx-border-width: 0px 0px 1px 0px; -fx-background-color: #313131; -fx-background-radius: 5px 5px 0px 0px; -fx-text-fill: #F4F9F1"
            );
        }
    }

    // Edit photo design
    @FXML
    void editPhotoInHover(MouseEvent event) {
        if (editProfileIsClicked) {
            editPhotoIcon.setStyle(
                "-fx-cursor: hand"
            );
            editPhotoIcon.setOpacity(1);
            profilePhoto.setStyle(
                "-fx-cursor: hand"
            );
            profilePhoto.setOpacity(0.75);
        }
    }

    @FXML
    void editPhotoOutHover(MouseEvent event) {
        if (editProfileIsClicked) {
            editPhotoIcon.setStyle(
                "-fx-cursor: default"
            );
            editPhotoIcon.setOpacity(0.75);
            profilePhoto.setStyle(
                "-fx-cursor: default"
            );
            profilePhoto.setOpacity(0.25);
        }
    }

    // Cancel Subscription design
    @FXML
    void cancelSubOnClicked(MouseEvent event) {
        
    }

    @FXML
    void cancelSubInHover(MouseEvent event) {
        subscriptionButton.setStyle(
            "-fx-cursor: hand"
        );
        subscriptionButton.setEffect(new Glow(0.3));
    }

    @FXML
    void cancelSubOutHover(MouseEvent event) {
        subscriptionButton.setStyle(
            "-fx-cursor: default"
        );
    }

}
