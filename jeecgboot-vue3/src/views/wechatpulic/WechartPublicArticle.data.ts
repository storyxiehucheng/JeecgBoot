import { BasicColumn } from '/@/components/Table';
import { FormSchema } from '/@/components/Table';
//列表数据
export const columns: BasicColumn[] = [
  {
    title: '文章id',
    align: 'center',
    dataIndex: 'articleId',
  },
  {
    title: '作者',
    align: 'center',
    dataIndex: 'author',
  },
  {
    title: '图文消息摘要',
    align: 'center',
    dataIndex: 'digest',
  },
  {
    title: '文章标题',
    align: 'center',
    dataIndex: 'title',
  },
  {
    title: '文章内容',
    align: 'center',
    dataIndex: 'content',
  },
  {
    title: '文章链接',
    align: 'center',
    dataIndex: 'titleUrl',
  },
  {
    title: '文章分类',
    align: 'center',
    dataIndex: 'category',
  },
  {
    title: '封面图片的URL',
    align: 'center',
    dataIndex: 'thumbUrl',
  },
];
//查询数据
export const searchFormSchema: FormSchema[] = [];
//表单数据
export const formSchema: FormSchema[] = [
  {
    label: '文章id',
    field: 'articleId',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入文章id!' }];
    },
  },
  {
    label: '作者',
    field: 'author',
    component: 'Input',
  },
  {
    label: '图文消息摘要',
    field: 'digest',
    component: 'Input',
  },
  {
    label: '文章标题',
    field: 'title',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入文章标题!' }];
    },
  },
  {
    label: '文章内容',
    field: 'content',
    component: 'Input',
  },
  {
    label: '文章链接',
    field: 'titleUrl',
    component: 'Input',
    dynamicRules: ({ model, schema }) => {
      return [{ required: true, message: '请输入文章链接!' }];
    },
  },
  {
    label: '文章分类',
    field: 'category',
    component: 'Input',
  },
  {
    label: '封面图片的URL',
    field: 'thumbUrl',
    component: 'Input',
  },
  // TODO 主键隐藏字段，目前写死为ID
  {
    label: '',
    field: 'id',
    component: 'Input',
    show: false,
  },
];

// 高级查询数据
export const superQuerySchema = {
  articleId: { title: '文章id', order: 0, view: 'text', type: 'string' },
  author: { title: '作者', order: 1, view: 'text', type: 'string' },
  digest: { title: '图文消息摘要', order: 2, view: 'text', type: 'string' },
  title: { title: '文章标题', order: 3, view: 'text', type: 'string' },
  content: { title: '文章内容', order: 4, view: 'text', type: 'string' },
  titleUrl: { title: '文章链接', order: 5, view: 'text', type: 'string' },
  category: { title: '文章分类', order: 6, view: 'text', type: 'string' },
  thumbUrl: { title: '封面图片的URL', order: 7, view: 'text', type: 'string' },
};

/**
 * 流程表单调用这个方法获取formSchema
 * @param param
 */
export function getBpmFormSchema(_formData): FormSchema[] {
  // 默认和原始表单保持一致 如果流程中配置了权限数据，这里需要单独处理formSchema
  return formSchema;
}
