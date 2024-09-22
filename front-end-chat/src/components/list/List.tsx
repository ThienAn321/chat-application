import { useEffect } from 'react';
import UserInfo from '../userInfo/UserInfo';
import ChatList from '../chatList/ChatList';
import userApi from '../../api/user/userApi';
import { useChatRoom } from '../../context/ChatRoomContext';

const List = () => {
  const { setCurrentUserId } = useChatRoom();

  const fetchUserDetails = async (): Promise<void> => {
    const { ok, body } = await userApi.fetchUserDetails();
    if (ok && body) {
      setCurrentUserId(body.id);
    }
  };

  useEffect(() => {
    fetchUserDetails();
  }, []);

  return (
    <div className="list">
      <UserInfo />
      <ChatList />
    </div>
  );
};

export default List;
