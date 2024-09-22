import { HttpResponse } from "../../models/http";
import { UserResponse, UserResponseSearch } from "../../models/user/user";
import { axiosClient, handleRequest } from "../axiosClient";
import config from '../config/config.json';

const userApi = {
  fetchRandomUsers: (): Promise<HttpResponse<UserResponse[]>> => {
    const url = config.apiBaseUrl.private + `/users/random`;
    return handleRequest(axiosClient.get(url));
  },
  fetchUserDetails: (): Promise<HttpResponse<UserResponse>> => {
    const url = config.apiBaseUrl.private + `/users`;
    return handleRequest(axiosClient.get(url));
  },
  searchByUserName: (username: string): Promise<HttpResponse<UserResponseSearch[]>> => {
    const url = config.apiBaseUrl.private + `/users/search`;
    return handleRequest(axiosClient.get(url, { params: { username } }));
  },
}

export default userApi;