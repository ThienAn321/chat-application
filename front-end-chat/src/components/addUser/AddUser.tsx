import React, { useEffect, useState } from 'react';
import { UserResponseSearch } from '../../models/user/user';
import userApi from '../../api/user/userApi';
import chatRoomApi from '../../api/chatroom/chatRoomApi';
import { toast } from 'react-toastify';
import { useChatRoom } from '../../context/ChatRoomContext';

interface IAddUserProps {
  addUserDivRef: React.MutableRefObject<HTMLDivElement | null>;
  setAddMode: (mode: boolean) => void;
}

const AddUser: React.FC<IAddUserProps> = ({ addUserDivRef, setAddMode }) => {
  const { fetchChatRoom } = useChatRoom();
  const [users, setUsers] = useState<UserResponseSearch[] | null>(null);
  const [text, setText] = useState<string>('');

  useEffect(() => {
    const fetchRandomUsers = async (): Promise<void> => {
      const { ok, body } = await userApi.fetchRandomUsers();
      if (ok && body) {
        setUsers(body.map((user) => ({ ...user, added: false })));
      }
    };

    fetchRandomUsers();
  }, []);

  const handleClickAddUser = async (userId: number): Promise<void> => {
    const { ok } = await chatRoomApi.createChatRoom({ userId });
    if (ok) {
      toast.success('Add user to chat successfully');
      fetchChatRoom();
      setAddMode(false);
    }
  };

  const handleSearch = async (e: React.FormEvent): Promise<void> => {
    e.preventDefault();
    if (text.trim()) {
      const { ok, body } = await userApi.searchByUserName(text.trim());
      if (ok && body) {
        setUsers(body);
      }
    }
  };

  return (
    <div className="add-user" ref={addUserDivRef}>
      <form onSubmit={handleSearch}>
        <input
          type="text"
          placeholder="Search username"
          name="username"
          autoComplete="off"
          value={text}
          onChange={(e) => setText(e.target.value)}
        />
        <button type="submit">Search</button>
      </form>
      <div className="text">
        <span>People You Might Know</span>
      </div>
      {users &&
        users.map((user) => {
          return (
            <>
              <div className="user">
                <div className="detail">
                  <img src={user.avatar || './avatar.png'} alt="" />
                  <span>{user.username}</span>
                </div>
                <button
                  disabled={user.added}
                  onClick={() => handleClickAddUser(user.id)}
                >
                  {user.added ? 'Added' : 'Add User'}
                </button>
              </div>
            </>
          );
        })}
    </div>
  );
};

export default AddUser;
