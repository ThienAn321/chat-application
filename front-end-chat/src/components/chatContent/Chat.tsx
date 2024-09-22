import UserInfoChat from '../common/UserInfoChat';
import MessageInput from '../common/MessageInput';
import MessageArea from '../common/MessageArea';

const Chat = () => {
  return (
    <div className="chat">
      <div className="top">
        <UserInfoChat />
      </div>
      <div className="center">
        <MessageArea />
      </div>
      <div className="bottom">
        <MessageInput />
      </div>
    </div>
  );
};

export default Chat;
