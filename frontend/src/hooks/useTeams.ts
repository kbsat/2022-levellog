import React, { useState, useEffect, useRef } from 'react';
import { useNavigate, useParams, useLocation } from 'react-router-dom';

import axios, { AxiosResponse } from 'axios';

import { MESSAGE, ROUTES_PATH } from 'constants/constants';

import useUser from './useUser';
import {
  requestGetTeams,
  requestGetTeam,
  requestPostTeam,
  requestDeleteTeam,
  requestEditTeam,
  requestCloseTeamInterview,
} from 'apis/teams';
import { MemberType } from 'types/member';
import { InterviewTeamType, TeamApiType, TeamCustomHookType, TeamEditApiType } from 'types/team';

export const useTeams = () => {
  const [teams, setTeams] = useState<InterviewTeamType[]>([]);
  const navigate = useNavigate();
  const accessToken = localStorage.getItem('accessToken');

  const getTeams = async () => {
    try {
      const res = await requestGetTeams({ accessToken });
      setTeams(res.data.teams);
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const responseBody: AxiosResponse = err.response!;
        if (err instanceof Error) alert(responseBody.data.message);
        navigate(ROUTES_PATH.HOME);
      }
    }
  };

  const handleClickInterviewGroup = (e: React.MouseEvent<HTMLElement>) => {
    const target = e.target as HTMLElement;
    const team = teams.find((team) => +team.id === +target.id);

    navigate(`/interview/teams/${target.id}`, {
      state: team,
    });
  };

  return {
    teams,
    getTeams,
    handleClickInterviewGroup,
  };
};

export const useTeam = () => {
  const { loginUserId } = useUser();
  const [team, setTeam] = useState<InterviewTeamType | Object>({});
  // 나중에 location은 타입을 고칠 필요가 있어보임
  const location = useLocation() as { state: InterviewTeamType };
  const teamInfoRef = useRef<HTMLInputElement[]>([]);
  const { teamId } = useParams();
  const navigate = useNavigate();
  const teamLocationState: InterviewTeamType | undefined = location.state;
  const accessToken = localStorage.getItem('accessToken');

  const postTeam = async ({ teamInfo }: Record<'teamInfo', TeamCustomHookType>) => {
    try {
      teamInfo.participants.ids = teamInfo.participants.ids.filter((id) => id !== loginUserId);
      await requestPostTeam({ teamInfo, accessToken });
      alert(MESSAGE.TEAM_CREATE);
      navigate(ROUTES_PATH.HOME);
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const responseBody: AxiosResponse = err.response!;
        if (err instanceof Error) alert(responseBody.data.message);
      }
    }
  };

  const getTeam = async () => {
    try {
      if (typeof teamId === 'string') {
        const res = await requestGetTeam({ teamId, accessToken });
        setTeam(res.data);

        return res.data;
      }
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const responseBody: AxiosResponse = err.response!;
        if (err instanceof Error) alert(responseBody.data.message);
        navigate(ROUTES_PATH.HOME);
      }
    }
  };

  const editTeam = async ({ teamInfo }: Pick<TeamEditApiType, 'teamInfo'>) => {
    try {
      if (typeof teamId !== 'string') throw Error;
      await requestEditTeam({ teamId, teamInfo, accessToken });
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const responseBody: AxiosResponse = err.response!;
        if (err instanceof Error) alert(responseBody.data.message);
      }
    }
  };

  const deleteTeam = async ({ teamId }: Pick<TeamApiType, 'teamId'>) => {
    try {
      await requestDeleteTeam({ teamId, accessToken });
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const responseBody: AxiosResponse = err.response!;
        if (err instanceof Error) alert(responseBody.data.message);
        navigate(ROUTES_PATH.HOME);
      }
    }
  };

  const closeTeamInterview = async ({ teamId }: Pick<TeamApiType, 'teamId'>) => {
    try {
      await requestCloseTeamInterview({ teamId, accessToken });
    } catch (err: unknown) {
      if (axios.isAxiosError(err)) {
        const responseBody: AxiosResponse = err.response!;
        if (err instanceof Error) alert(responseBody.data.message);
        navigate(ROUTES_PATH.HOME);
      }
    }
  };

  const onSubmitTeamAddForm = async ({ participants }: Record<'participants', MemberType[]>) => {
    const [title, place, date, time, interviewerNumber] = teamInfoRef.current;
    const teamInfo = {
      title: title.value,
      place: place.value,
      startAt: `${date.value}T${time.value}`,
      interviewerNumber: interviewerNumber.value,
      participants: {
        ids: Object.values(participants).map((participants) => participants.id),
      },
    };
    await postTeam({ teamInfo });
  };

  const onSubmitTeamEditForm = async () => {
    const [title, place, date, time] = teamInfoRef.current;
    const teamInfo = {
      title: title.value,
      place: place.value,
      startAt: `${date.value}T${time.value}`,
    };
    await editTeam({ teamInfo });
    navigate(ROUTES_PATH.HOME);
  };

  const onClickDeleteTeamButton = async ({ teamId }: Pick<TeamApiType, 'teamId'>) => {
    if (confirm(MESSAGE.TEAM_DELETE_CONFIRM)) {
      await deleteTeam({ teamId });
      navigate(ROUTES_PATH.HOME);

      return;
    }
  };

  const onClickCloseTeamInterviewButton = async ({ teamId }: Pick<TeamApiType, 'teamId'>) => {
    if (confirm(MESSAGE.INTERVIEW_CLOSE_CONFIRM)) {
      await closeTeamInterview({ teamId });
      navigate(ROUTES_PATH.HOME);

      return;
    }
  };

  const getTeamOnRef = async () => {
    const team = await getTeam();
    if (team && Object.keys(team).length === 0) {
      return;
    }
    teamInfoRef.current[0].value = (team as unknown as InterviewTeamType).title;
    teamInfoRef.current[1].value = (team as unknown as InterviewTeamType).place;
    teamInfoRef.current[2].value = (team as unknown as InterviewTeamType).startAt.slice(0, 10);
    teamInfoRef.current[3].value = (team as unknown as InterviewTeamType).startAt.slice(-8);
  };

  useEffect(() => {
    // 나중에 location은 타입을 고칠 필요가 있어보임
    if (teamLocationState && (teamLocationState as InterviewTeamType).id !== undefined) {
      setTeam(teamLocationState);
    }
  }, []);

  return {
    teamLocationState,
    team,
    getTeam,
    teamInfoRef,
    getTeamOnRef,
    onSubmitTeamAddForm,
    onSubmitTeamEditForm,
    onClickDeleteTeamButton,
    onClickCloseTeamInterviewButton,
  };
};
