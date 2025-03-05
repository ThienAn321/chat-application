import UserInfoChat from '../common/UserInfoChat';
import MessageInput from '../common/MessageInput';
import MessageArea from '../common/MessageArea';
import { useState } from 'react';

const Chat = () => {
  const [loading, setLoading] = useState<boolean>(false);
  const [progress, setProgress] = useState<number>(0);
  return (
    <div className="chat">
      <div className="top">
        <UserInfoChat />
      </div>
      <div className="center">
        <MessageArea loading={loading} progress={progress} />
      </div>
      <div className="bottom">
        <MessageInput setLoading={setLoading} setProgress={setProgress} />
      </div>
    </div>
  );
};

export default Chat;
