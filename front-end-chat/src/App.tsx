import List from './components/list/List';
import Chat from './components/chatContent/Chat';
import Auth from './components/auth';
import Notification from './components/notification/Notification';
import { RootState } from './redux/store';
import { useSelector } from 'react-redux';
import { useChatRoom } from './context/ChatRoomContext';

function App() {
  const { isAuthenticated, isLoading } = useSelector(
    (state: RootState) => state.auth
  );
  const { selectedChatRoom } = useChatRoom();

  if (isLoading) return <div className="loading">Loading...</div>;

  return (
    <div className="container-wrapper">
      <div className="container">
        {isAuthenticated ? (
          <>
            <List />
            {selectedChatRoom && <Chat />}
          </>
        ) : (
          <Auth />
        )}
      </div>
      <div className="notification-wrapper">
        <Notification />
      </div>
    </div>
  );
}

export default App;
