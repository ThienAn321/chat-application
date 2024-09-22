export interface RegistrationRequest {
  email: string;
  username: string;
  password: string;
  avatar: string;
}

export interface AuthenticationRequest {
  email: string;
  password: string;
}

export interface AuthenticationResponse {
  userId: number;
  token: string;
  refreshToken: string;
  expiredTime: string;
}

export interface RefreshTokenRequest {
  refreshToken: string;
}

export interface RefreshTokenResponse {
  accessToken: string;
}
