import { useEffect, useMemo, useState } from 'react';
import userApi from '../../api/user/userApi';
import { UserResponse } from '../../models/user/user';
import { useDispatch } from 'react-redux';
import { logout } from '../../redux/slice/authSlice';
import { disconnectWebSocket } from '../../utils/webSocketClient';
import { useChatRoom } from '../../context/ChatRoomContext';
import { toast } from 'react-toastify';

const UserInfo = () => {
  const dispatch = useDispatch();
  const { setCurrentUserId } = useChatRoom();
  const [userDetails, setUserDetails] = useState<UserResponse | null>(null);
  const memoizedUserDetails = useMemo(() => userDetails, [userDetails]);

  const handleLogout = (): void => {
    dispatch(logout());
    disconnectWebSocket();
    setCurrentUserId(null);
    toast.success('Logout successfully');
  };

  useEffect(() => {
    const fetchUserDetails = async (): Promise<void> => {
      const { ok, body } = await userApi.fetchUserDetails();
      if (ok && body) {
        setUserDetails(body);
      }
    };

    fetchUserDetails();
  }, []);

  return (
    <div className="user-info">
      {memoizedUserDetails && (
        <>
          <div className="user">
            <img src={memoizedUserDetails.avatar || './avatar.png'} alt="" />
            <h2>{memoizedUserDetails.username}</h2>
          </div>
          <div className="icons">
            <button className='btn-logout' onClick={handleLogout}>Logout</button>
          </div>
        </>
      )}
    </div>
  );
};

export default UserInfo;
