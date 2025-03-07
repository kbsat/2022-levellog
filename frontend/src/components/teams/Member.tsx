import { Dispatch, SetStateAction } from 'react';

import styled from 'styled-components';

import Image from 'components/@commons/Image';
import { MemberType } from 'types/member';

const Member = ({ member, setNicknameValue, updateParticipants }: MemberProps) => {
  const { id, nickname, profileUrl } = member;

  const handleClickMember = () => {
    setNicknameValue('');
    updateParticipants({ id, nickname, profileUrl });
  };

  return (
    <S.MemberButton type={'button'} onClick={handleClickMember}>
      <Image src={profileUrl} sizes={'SMALL'} />
      <S.Nickname>{nickname}</S.Nickname>
    </S.MemberButton>
  );
};

interface MemberProps {
  member: MemberType;
  setNicknameValue: Dispatch<SetStateAction<string>>;
  updateParticipants: ({ id, nickname, profileUrl }: MemberType) => void;
}

const S = {
  MemberButton: styled.button`
    display: flex;
    align-items: center;
    gap: 0.5rem;
    width: 100%;
    height: 2.5rem;
    border-style: none;
    background-color: ${(props) => props.theme.default.WHITE};
  `,

  Nickname: styled.p`
    font-family: 'Font_Regular';
    font-size: 1rem;
    font-weight: 600;
  `,
};

export default Member;
