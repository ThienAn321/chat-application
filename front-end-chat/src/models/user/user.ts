export interface UserResponse {
  id: number;
  username: string;
  email: string;
  avatar: string;
}

export interface UserResponseSearch extends UserResponse{
  added: boolean;
}