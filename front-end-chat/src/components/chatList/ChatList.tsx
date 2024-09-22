import { useEffect, useRef, useState } from 'react';
import SearchBar from '../common/SearchBar';
import UserList from '../userList/UserList';
import AddUser from '../addUser/AddUser';
import React from 'react';

const ChatList = React.memo(() => {
  const [addMode, setAddMode] = useState<boolean>(false);
  const addUserDivRef = useRef<HTMLDivElement | null>(null);
  const addButtonRef = useRef<HTMLImageElement | null>(null);

  useEffect(() => {
    const handleClickOutside = (event: MouseEvent) => {
      if (
        addUserDivRef.current &&
        !addUserDivRef.current.contains(event.target as Node) &&
        addButtonRef.current &&
        !addButtonRef.current.contains(event.target as Node)
      ) {
        setAddMode(false);
      }
    };

    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  return (
    <div className="chat-list">
      <SearchBar addMode={addMode} setAddMode={setAddMode} addButtonRef={addButtonRef} />
      <UserList />
      {addMode && <AddUser addUserDivRef={addUserDivRef} setAddMode={setAddMode} />}
    </div>
  );
});

export default ChatList;
