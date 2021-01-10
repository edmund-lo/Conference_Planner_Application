package toolbar;

import common.ILoggedInView;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ToolBar;
import javafx.scene.layout.HBox;

public interface IToolbarView extends ILoggedInView {
    HBox getOrganizerGroup();
    HBox getSpeakerGroup();
    HBox getAdminGroup();
    ToolBar getToolBar();

    //region Sets all button actions
    void setHomeButtonAction(EventHandler<ActionEvent> eventHandler);
    void setViewScheduleButtonAction(EventHandler<ActionEvent> eventHandler);
    void setViewEventsButtonAction(EventHandler<ActionEvent> eventHandler);
    void setMessagingButtonAction(EventHandler<ActionEvent> eventHandler);
    void setFriendsButtonAction(EventHandler<ActionEvent> eventHandler);
    void setCreateRoomButtonAction(EventHandler<ActionEvent> eventHandler);
    void setCreateEventButtonAction(EventHandler<ActionEvent> eventHandler);
    void setScheduleSpeakerButtonAction(EventHandler<ActionEvent> eventHandler);
    void setCancelEventButtonAction(EventHandler<ActionEvent> eventHandler);
    void setMessageSpeakersButtonAction(EventHandler<ActionEvent> eventHandler);
    void setMessageAttendeesButtonAction(EventHandler<ActionEvent> eventHandler);
    void setSpeakerEventsButtonAction(EventHandler<ActionEvent> eventHandler);
    void setCreateAccountButtonAction(EventHandler<ActionEvent> eventHandler);
    void setUnlockAccountsButtonAction(EventHandler<ActionEvent> eventHandler);
    void setSetVipButtonAction(EventHandler<ActionEvent> eventHandler);
    void setDeleteMessagesButtonAction(EventHandler<ActionEvent> eventHandler);
    void setRemoveEventsButtonAction(EventHandler<ActionEvent> eventHandler);
    void setLogoutButtonAction(EventHandler<ActionEvent> eventHandler);
    //endregion
}
