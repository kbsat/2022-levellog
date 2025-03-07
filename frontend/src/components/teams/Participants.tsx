import styled from 'styled-components';

import useUser from 'hooks/useUser';

import Button from 'components/@commons/Button';
import Image from 'components/@commons/Image';
import { MemberType } from 'types/member';

const Participant = ({ participant, updateParticipants }: ParticipantProps) => {
  const { loginUserId } = useUser();
  const { id, nickname, profileUrl } = participant;

  const handleClickDeleteButton = () => {
    updateParticipants({ id, nickname, profileUrl });
  };

  return (
    <S.Container>
      <Image src={profileUrl} sizes={'SMALL'} />
      <S.Nickname>{nickname}</S.Nickname>
      {loginUserId !== id && <S.DeleteButton onClick={handleClickDeleteButton}>X</S.DeleteButton>}
    </S.Container>
  );
};

interface ParticipantProps {
  participant: MemberType;
  updateParticipants: ({ id, nickname, profileUrl }: MemberType) => void;
}

const S = {
  Container: styled.div`
    display: flex;
    align-items: center;
    gap: 0.25rem;
    margin-bottom: 0.625rem;
    padding: 0.5rem;
    box-shadow: 0.25rem 0.25rem 0.25rem ${(props) => props.theme.default.GRAY};
    border-style: none;
    border-radius: 1.5625rem;
    background-color: ${(props) => props.theme.default.WHITE};
    font-size: 1rem;
  `,

  Nickname: styled.p`
    font-size: 1rem;
    font-weight: 600;
  `,

  DeleteButton: styled(Button)`
    background-color: ${(props) => props.theme.default.WHITE};
    border: none;
    padding: 0 0.125rem 0 0.125rem;
    color: ${(props) => props.theme.default.RED};
  `,
};

export default Participant;
