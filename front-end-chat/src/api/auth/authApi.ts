import { AxiosResponse } from "axios";
import { RegistrationRequest, AuthenticationRequest, AuthenticationResponse, RefreshTokenRequest, RefreshTokenResponse } from "../../models/auth/auth";
import { HttpResponse } from "../../models/http";
import { axiosClient, axiosRefresh, handleRequest } from "../axiosClient";
import config from '../config/config.json';

const authApi = {
  register: (body: RegistrationRequest): Promise<HttpResponse<void>> => {
    const url = config.apiBaseUrl.public + `/register`;
    return handleRequest(axiosClient.post(url, body));
  },
  login: (body: AuthenticationRequest): Promise<HttpResponse<AuthenticationResponse>> => {
    const url = config.apiBaseUrl.public + `/login`;
    return handleRequest(axiosClient.post(url, body));
  },
  refreshToken: (body: RefreshTokenRequest): Promise<AxiosResponse<RefreshTokenResponse>> => {
    const url = config.apiBaseUrl.public + `/refresh`;
    return axiosRefresh.post(url, body);
  }
}

export default authApi;