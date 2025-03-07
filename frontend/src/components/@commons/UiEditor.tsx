import { LegacyRef } from 'react';

import styled from 'styled-components';

import '@toast-ui/editor/dist/toastui-editor.css';
import { Editor } from '@toast-ui/react-editor';

const UiEditor = ({
  needToolbar,
  autoFocus,
  height,
  contentRef,
  initialEditType,
  toolbarItems = [
    ['heading', 'bold', 'italic', 'strike'],
    ['hr', 'quote'],
    ['ul', 'ol', 'task', 'indent', 'outdent'],
    ['table', 'image', 'link'],
    ['code', 'codeblock'],
    ['scrollSync'],
  ],
}: UiEditorProps) => {
  return (
    <S.Container needToolbar={needToolbar}>
      <Editor
        autofocus={autoFocus}
        previewStyle={'tab'}
        height={height}
        initialEditType={initialEditType}
        initialValue={' '}
        ref={contentRef}
        hideModeSwitch={true}
        toolbarItems={toolbarItems}
        usageStatistics={false}
      />
    </S.Container>
  );
};

interface UiEditorProps {
  needToolbar: boolean;
  autoFocus: boolean;
  height: string;
  contentRef: LegacyRef<Editor>;
  initialEditType: 'markdown' | 'wysiwyg';
  toolbarItems?: Array<Array<string>>;
}

interface ContainerProps {
  needToolbar: boolean;
}

const S = {
  Container: styled.div<ContainerProps>`
    .toastui-editor-toolbar {
      display: ${(props) => (props.needToolbar ? 'block' : 'none')};
    }
    .toastui-editor-md-container {
      background-color: #ffffff;
    }
  `,
};

export default UiEditor;
