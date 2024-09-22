import React from 'react';
import { useChatRoom } from '../../context/ChatRoomContext';

interface ISearchBarProps {
  addMode: boolean;
  setAddMode: (mode: boolean) => void;
  addButtonRef: React.MutableRefObject<HTMLImageElement | null>;
}

const SearchBar: React.FC<ISearchBarProps> = ({
  addMode,
  setAddMode,
  addButtonRef,
}) => {
  const { searchQuery, setSearchQuery } = useChatRoom();
  const handleClick = (): void => {
    setAddMode(!addMode);
  };
  const handleSearchChange = (e: React.ChangeEvent<HTMLInputElement>): void => {
    setSearchQuery(e.target.value);
  };

  return (
    <div className="search">
      <div className="search-bar">
        <img src="./search.png" alt="" />
        <input
          type="text"
          placeholder="Search"
          autoComplete="off"
          value={searchQuery}
          onChange={handleSearchChange}
        />
      </div>
      <img
        className="add"
        src={addMode ? './minus.png' : './plus.png'}
        alt=""
        onClick={handleClick}
        ref={addButtonRef}
      />
    </div>
  );
};

export default SearchBar;
