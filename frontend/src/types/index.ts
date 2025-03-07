export interface UserInfoType {
  id: string;
  nickname: string;
  profileUrl: string;
}

export type ImageSizeType = 'HUGE' | 'LARGE' | 'MEDIUM' | 'SMALL';

export interface RequestType {
  accessToken: string | null;
  method: 'get' | 'post' | 'put' | 'delete';
  url: string;
  headers: {
    Authorization: string;
  };
}
